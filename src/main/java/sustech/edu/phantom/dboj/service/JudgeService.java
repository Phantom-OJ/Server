package sustech.edu.phantom.dboj.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.basicJudge.JudgeInput;
import sustech.edu.phantom.dboj.basicJudge.JudgeResult;
import sustech.edu.phantom.dboj.entity.Code;
import sustech.edu.phantom.dboj.entity.JudgePoint;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.mapper.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class JudgeService {
    private final static int AC = 0;// accept
    private final static int SE = 1;// system error
    private final static int TLE = 2;// time limit exceed
    private final static int WA = 3;// wrong answer
    private final static int MLE = 4;// memory limit exceed
    private final static int RE = 5;// runtime error

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

    private List<JudgeResult> judgeResults = new ArrayList<>();

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
    public void judgeCode(int id, CodeForm codeForm, int userId) {
        Code c = Code.builder()
                .code(codeForm.getCode())
                .codeLength(codeForm.getCode().getBytes(StandardCharsets.UTF_8).length)
                .submitTime(codeForm.getSubmitTime())
                .build();
        codeMapper.saveCode(c);


        Record record = new Record();
        Problem problem = problemMapper.queryCurrentProblem(id);
        List<JudgeInput> judgeInputList = new ArrayList<>();

        List<JudgePoint> judgePointList = judgePointMapper.getAllJudgePointsOfProblem(id);
        for (JudgePoint j : judgePointList) {
            ArrayList<String> answer = new ArrayList<>();
            answer.add(j.getAnswer());
            String dbPath = judgeDatabaseMapper.getJudgeDatabaseById(j.getJudgeDatabaseId()).getDatabaseUrl();
            judgeInputList.add(JudgeInput.builder()
                    .JudgeDatabase(dbPath)
                    .beforeInput(j.getBeforeSql())
                    .userInput(codeForm.getCode())
                    .afterInput(j.getAfterSql())
                    .timeLimit(problem.getTimeLimit())
                    .standardAnswer(answer)
                    .build());
        }
        // socket.send()


    }

    public Record receiveJudgeResult(List<JudgeResult> judgeResults) {
        // 先build好已知的变量
        Record record = Record.builder()
                .codeId(1)
                .userId(1)
                .problemId(1)
                .dialect("known")
                .codeLength(1)
                .submitTime(1L).
                        build();
        StringBuilder totalDescription = new StringBuilder();
        long score = 0;
        String result = "";
        long time = 0, space = 0;

        for (JudgeResult j : judgeResults) {
            time = Math.max(time, j.getRunTime());
//            time += j.getRunTime();
//            time +=j.getTime();
//            space += j.getSpace();
            totalDescription.append(j.getCodeDescription());
            totalDescription.append("\n");
        }
        recordMapper.saveRecord(record);


        return null;
    }

    public JudgeResult judgeOnePoint() {
        return null;
    }

}

class Buffer {
    private static int productNumber = 0;
    private static final int MAX = 20;//队列长度
    public static Queue<JudgeInput> judgeInputQueue = new ArrayBlockingQueue<>(MAX);


    public synchronized void addSQLCode() throws InterruptedException {
        while (productNumber == MAX) {
            System.out.printf("目前队列中还有%d份代码没有评测，进入阻塞状态\n", productNumber);
            this.wait();
        }
        System.out.printf("目前队列中还有%d份代码没有评测\n", productNumber);
        ++productNumber;
        this.notifyAll();
    }

    public synchronized void judgeSQLCode() throws InterruptedException {
        while (productNumber == 0) {
            System.out.println("目前没有代码了");
            this.wait();
        }
        --productNumber;
        System.out.println();
    }
}

class Producer implements Runnable {
    private Buffer product;

    public Producer(Buffer product) {
        this.product = product;
    }

    @Override
    public void run() {
        while (true) {
            try {
                product.addSQLCode();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        while (true) {
            consumer.judgeSQLCode();
        }
    }
}