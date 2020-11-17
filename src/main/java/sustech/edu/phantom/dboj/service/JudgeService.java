package sustech.edu.phantom.dboj.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.basicJudge.JudgeInput;
import sustech.edu.phantom.dboj.basicJudge.JudgeResult;
import sustech.edu.phantom.dboj.entity.JudgePoint;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.mapper.JudgeDatabaseMapper;
import sustech.edu.phantom.dboj.mapper.JudgePointMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JudgeService {
    private final static int AC = 0;
    private final static int SE = 1;
    private final static int TLE = 2;
    private final static int WA = 3;
    private final static int MLE = 4;
    private final static int RE = 5;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    JudgePointMapper judgePointMapper;

    @Autowired
    JudgeDatabaseMapper judgeDatabaseMapper;

    private List<JudgeResult> judgeResults = new ArrayList<>();

    /**
     * @param id problem id
     */
    public void judgeCode(int id, String code) {
        Problem problem = problemMapper.queryCurrentProblem(id);
        List<JudgeInput> judgeInputList = new ArrayList<>();

        List<JudgePoint> judgePointList = judgePointMapper.getAllJudgePointsOfProblem(id);
        for (JudgePoint j : judgePointList) {
            ArrayList<String> answer = new ArrayList<>();
            answer.add(j.getAnswer());
            String dbPath = judgeDatabaseMapper.getJudgeDatabaseById(j.getJudgeDatabaseId()).getDatabasePath();
            judgeInputList.add(JudgeInput.builder()
                    .JudgeDatabase(dbPath)
                    .beforeInput(j.getBeforeSql())
                    .userInput(code)
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
            time += j.getRunTime();
//            time +=j.getTime();
//            space += j.getSpace();
            totalDescription.append(j.getCodeDescription());
        }
        return null;
    }

    public JudgeResult judgeOnePoint() {
        return null;
    }

}

class SQLCode{
    private static int productNumber = 0;
    private static final int MAX = 20;//队列长度

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
        System.out.println();
    }
}

class Producer implements Runnable{
    private SQLCode product;

    public Producer(SQLCode product) {
        this.product = product;
    }

    @Override
    public void run(){
        while (true) {
            try {
                product.addSQLCode();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Consumer implements Runnable{
    private SQLCode consumer;

    public Consumer(SQLCode consumer) {
        this.consumer = consumer;
    }
    @SneakyThrows
    @Override
    public void run(){
        while (true){
            consumer.judgeSQLCode();
        }
    }
}