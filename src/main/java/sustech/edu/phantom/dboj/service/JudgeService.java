package sustech.edu.phantom.dboj.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.basicJudge.FastLinux;
import sustech.edu.phantom.dboj.basicJudge.JudgeInput;
import sustech.edu.phantom.dboj.basicJudge.JudgeResult;
import sustech.edu.phantom.dboj.entity.*;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.mapper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class JudgeService {
    private final static int AC = 0;// accept
    private final static int SE = 1;// system error
    private final static int TLE = 2;// time limit exceed
    private final static int WA = 3;// wrong answer
    private final static int RE = 4;// runtime error
    private final static int CNE = 5;// 连接失败
    private final static int UE = 6;//未知错误
    private final static int MLE = 9;// memory limit exceed


    static Buffer simpleQueue = new Buffer();
    static Producer producer = new Producer(simpleQueue);
    static Consumer consumer = new Consumer(simpleQueue);

    //    static {
//        new Thread(()->{
//            while (true){
//                try {
//                    simpleQueue.judgeSQLCode();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    JudgePointMapper judgePointMapper;

    @Autowired
    JudgeDatabaseMapper judgeDatabaseMapper;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RecordProblemMapper recordProblemMapper;
    private List<JudgeResult> judgeResults = new ArrayList<>();

    public static ArrayList<String> answerStringToArrayList(String textAnswer) {
        ArrayList<String> answer = new ArrayList<>();
        String[] rows = textAnswer.trim().split("\n");
        for (int i = 0; i < rows.length; i++) {
            String curentRow = rows[i].replace("\"", "").trim();
            answer.add(curentRow);
        }
        return answer;
    }

//    public  void addRequestToQueue(int id, CodeForm codeForm, int userId) {
//        JudgeMessage judgeMessage = new JudgeMessage(id, codeForm, userId);
//        simpleQueue.addSQLCode(judgeMessage);
//    }

    /**
     * 接受code (还有 problem id 和 user id)
     * -> 取出判题信息problem表、judge_database表 judge_point表
     * -> 插入code表
     * ->
     * -> 消息队列
     * -> 传给判题机判题
     * -> 消息队列
     * -> 得到判题结果
     * <p>
     * -> 更新problem(解题数、提交数)
     * -> 更新grade表(这题分数，有提交就更新，没有提交就插入，这里记录的是最高分，冗余表)
     * -> 更新record(提交记录)表
     *
     * @param id problem id
     */
    public void judgeCode(int id, CodeForm codeForm, int userId) throws IOException {
        System.err.println(codeForm.getSubmitTime());
        long start=System.currentTimeMillis();
        //FastLinux.createDatabase("12002");
        /*插入code表*/
        Code c = Code.builder()
                .code(codeForm.getCode())
                .codeLength(codeForm.getCode().getBytes(StandardCharsets.UTF_8).length)
                .submitTime(codeForm.getSubmitTime())
                .build();
        codeMapper.saveCode(c);
        System.out.println(c.getId());
        Problem problem = problemMapper.queryCurrentProblem(id);
        System.out.println(problem);
        List<JudgeInput> judgeInputList = new ArrayList<>();
        HashMap<String,Object> map=new HashMap<>();
        map.put("type",problem.getType());

        List<JudgePoint> judgePointList = judgePointMapper.getAllJudgePointsOfProblem(id);
        for (JudgePoint j : judgePointList) {
            ArrayList<String> answer = answerStringToArrayList(j.getAnswer());
            String dbPath = judgeDatabaseMapper.getJudgeDatabaseById(j.getJudgeDatabaseId()).getDatabaseUrl();
            JudgeInput currentInput = JudgeInput.builder()
                    .JudgeDatabase(dbPath)
                    .beforeInput(j.getBeforeSql())
                    .userInput(codeForm.getCode())
                    .afterInput(j.getAfterSql())
                    .timeLimit(problem.getTimeLimit())
                    .standardAnswer(answer)
                    .additionFields(map)
                    .build();
            judgeInputList.add(currentInput);
            System.out.println("测试点报文：" + currentInput);
        }
        int totalTestPoint = judgePointList.size();
        ArrayList<JudgeResult> judgeResults = new ArrayList<>();
        int acNum = 0;
        for (JudgeInput judgeInput : judgeInputList
        ) {

            JudgeResult judgeResult = sustech.edu.phantom.dboj.basicJudge.JudgeService.judgeDecide(judgeInput);

            //System.out.println(judgeResult);
            judgeResults.add(judgeResult);
            if (judgeResult.getCode() == 0) {
                acNum += 1;
            }
        }

        int score = 100 * acNum / totalTestPoint;
        /*1.更新proble表*/
        if (acNum == totalTestPoint) {
            problem.setNumberSubmit(problem.getNumberSubmit() + 1);
            problem.setNumberSolve(problem.getNumberSolve() + 1);
        } else {
            problem.setNumberSubmit(problem.getNumberSubmit() + 1);
        }
        String result = "WA";
        if (acNum == totalTestPoint) {
            result = "AC";
        }

        problemMapper.updateProblemInfo(problem);
        /*2.更新Record表*/
        StringBuilder totalDescription = new StringBuilder();
        long time = 0, space = 0;
        for (JudgeResult j : judgeResults) {
            time = Math.max(time, j.getRunTime());
            totalDescription.append(j.getCodeDescription());
            totalDescription.append("\n");
        }
        Record record = Record.builder()
                .codeId(c.getId())
                .userId(userId)
                .problemId(problem.getId())

                .codeLength(c.getCodeLength())
                .submitTime(codeForm.getSubmitTime()).
                        score(score).
                        result(result).
                        space(space).
                        time(time)
                .dialect("known").
                        build();
        recordMapper.saveRecord(record);
        /*3.更新grade表*/
        Grade oldGrade = gradeMapper.getOneGrade(userId, problem.getId());
        /*4.新增一张表*/
        ArrayList<RecordProblemJudgePoint> recordProblemJudgePoints=new ArrayList<>();
        for (int i=0;i<judgeResults.size();i++
             ) {
            JudgeResult judgeResult=judgeResults.get(i);

            RecordProblemJudgePoint recordProblemJudgePoint=
                    RecordProblemJudgePoint.builder()
                            .recordId(record.getId())
                            .problemId(problem.getId())
                            .judgePointIndex(i+1)
                            .time(judgeResult.getRunTime())
                            .space(100L).
                            result(codeToString(judgeResult.getCode()))
                            .description(judgeResult.getCodeDescription())
                            .build();
            recordProblemJudgePoints.add(recordProblemJudgePoint);

        }
        recordProblemMapper.saveOneRecordDetails(recordProblemJudgePoints);

        if (oldGrade == null) {
            Grade grade =
                    Grade.builder()
                            .userId(userId)
                            .problemId(problem.getId())
                            .score(score)
                            .build();
            gradeMapper.saveOneGrade(grade);
        } else {
            if (score > oldGrade.getScore()) {
                gradeMapper.updateOneGrade(userId, problem.getId(), score);
            }
        }
        long end=System.currentTimeMillis();
        System.out.println("判一次题时间:"+(end-start));

        FastLinux.executeDockerCmd("docker stop `docker ps -aq`");
        FastLinux.createDatabase("12002");

    }

    public static String codeToString(Integer code){
        String s="";
        switch (code){
            case AC:
                s="AC";
                break;
            case SE:
                s="SE";
                break;
            case TLE:
                s="TLE";
                break;
            case WA:
                s="WA";
                break;
            case RE:
                s="RE";
                break;
            case 5:
                s="CNE";
                break;//连接错误
            case 6:
                s="UE";
                break;
            default:
                s="UE";
                break;
        }
        return s;
    }

    public void receiveJudgeResult(List<JudgeResult> judgeResults, Record record) {
        // 先build好已知的变量

        StringBuilder totalDescription = new StringBuilder();
        long score = 0;
        String result = "";
        long time = 0, space = 0;

        for (JudgeResult j : judgeResults) {
            time = Math.max(time, j.getRunTime());
            totalDescription.append(j.getCodeDescription());
            totalDescription.append("\n");
        }
        recordMapper.saveRecord(record);

    }

    public JudgeResult judgeOnePoint() {
        return null;
    }


}

class Buffer {
    private static int productNumber = 0;
    private static final int MAX = 20;//队列长度
    public static Queue<JudgeMessage> judgeMessageQueue = new ArrayBlockingQueue<>(MAX);


    public synchronized void addSQLCode(JudgeMessage judgeMessage) {
        while (productNumber == MAX) {
            System.out.printf("目前队列中还有%d份判题请求没有评测，进入阻塞状态\n", productNumber);
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.printf("目前队列中还有%d份判题请求没有评测\n", productNumber);
        judgeMessageQueue.add(judgeMessage);
        ++productNumber;
        this.notifyAll();
    }

    public synchronized JudgeMessage judgeSQLCode() throws InterruptedException, SQLException {
        while (productNumber == 0) {
            System.out.println("目前没有代码了");
            this.wait();
        }
        JudgeMessage judgeMessage = judgeMessageQueue.poll();
        --productNumber;
        System.out.println("消费一次judgeInput:" + judgeMessage);
        this.notifyAll();
        return judgeMessage;
    }
}

class Producer implements Runnable {

    private Buffer product;
    private JudgeMessage judgeMessage;

    public void setProduct(Buffer product) {
        this.product = product;
    }

    public void setJudgeMessage(JudgeMessage judgeMessage) {
        this.judgeMessage = judgeMessage;
    }

    public Producer(Buffer product) {
        this.product = product;
    }

    @Override
    public void run() {

        product.addSQLCode(judgeMessage);


    }
}

class Consumer implements Runnable {
    private Buffer consumer;

    public Consumer(Buffer consumer) {
        this.consumer = consumer;
    }

    @SneakyThrows
    @Override
    public void run() {
        JudgeMessage judgeMessage = consumer.judgeSQLCode();


    }
}

class JudgeMessage {
    int id;
    CodeForm codeForm;
    int userId;

    public JudgeMessage() {
    }

    public JudgeMessage(int id, CodeForm codeForm, int userId) {
        this.id = id;
        this.codeForm = codeForm;
        this.userId = userId;
    }

}
