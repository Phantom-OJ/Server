package sustech.edu.phantom.dboj.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import sustech.edu.phantom.dboj.basicJudge.JudgeResult;
import sustech.edu.phantom.dboj.basicJudge.JudgeResultMessage;
import sustech.edu.phantom.dboj.basicJudge.PollingMessage;
import sustech.edu.phantom.dboj.entity.po.*;
import sustech.edu.phantom.dboj.mapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static sustech.edu.phantom.dboj.service.JudgeService.codeToString;

@Service
public class TestService implements Runnable{



    @Autowired
    RedisTemplate<String, Object> redisTemplate;


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

    Gson thegson;
    Jedis jedis;


    public TestService(){
        thegson=new Gson();
        jedis = new Jedis("10.20.87.51", 6379);
       Thread thread=new Thread(this);
       thread.start();
    }

    public PollingMessage getRecordStatusbyCodeId(String codeId){
        String p=(String) redisTemplate.opsForValue().get(codeId);
        if(p==null){
            return PollingMessage.createErrorMessage();
        }
        return thegson.fromJson(p,PollingMessage.class);
    }


    @SneakyThrows
    @Override
    public void run() {
        Gson gson=new Gson();
        while (true){
            try {
                jedis.connect();
                System.out.println("服务运行中...");
                List<String> s = jedis.brpop(0, "judge_result_list");
                for (int i = 1; i < s.size(); i++) {
                    JudgeResultMessage judgeResultMessage = gson.fromJson(s.get(i), JudgeResultMessage.class);
                    updateAfterJudge(judgeResultMessage);
                }
                Thread.sleep(1L);
            }
            catch (Exception e){
                e.printStackTrace();
                }

    }
    }

    public void sendRecord(String codeId){
        PollingMessage pollingMessage=PollingMessage.builder()
                .messageId(1)
                .description("pending")
                .recordId(-1)
                .build();
        redisTemplate.opsForValue().set(codeId,thegson.toJson(pollingMessage));
    }
    @Async
    public void updateAfterJudge(JudgeResultMessage message){
        int totalTestPoint=message.getJudgeResults().size();
        int problemId=message.getProblemId();
        int codeId=message.getCodeId();
        int userId=message.getUserId();
        Problem problem = problemMapper.queryCurrentProblem(problemId,false);
        Code c=codeMapper.queryCode(codeId);
        System.out.println(c);

        ArrayList<JudgeResult> judgeResults = message.getJudgeResults();
        int acNum = 0;
        for (JudgeResult judgeResult : judgeResults
        ) {
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
        for (int i =0; i<judgeResults.size();i++) {
            JudgeResult j=judgeResults.get(i);
            time = Math.max(time, j.getRunTime());
            totalDescription.append(j.getCodeDescription());
            totalDescription.append("\n");
        }
        Record record = Record.builder()
                .codeId(c.getId())
                .userId(userId)
                .problemId(problem.getId())

                .codeLength(c.getCodeLength())
                .submitTime(c.getSubmitTime()).
                        score(score).
                        result(result).
                        space(space).
                        time(time)
                .dialect(c.getDialect()).
                        build();

        recordMapper.saveRecord(record);
        /*3.更新grade表*/
        Grade oldGrade = gradeMapper.getOneGrade(userId, problem.getId());
        /*4.更新recordProblemJudgePoints*/
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
        PollingMessage pollingMessage=thegson.fromJson((String)redisTemplate.opsForValue().get(String.valueOf(codeId)),PollingMessage.class);
        pollingMessage.setRecordId(record.getId());
        pollingMessage.setDescription("Judge Complete");
        pollingMessage.setMessageId(0);
        redisTemplate.opsForValue().set(String.valueOf(codeId),thegson.toJson(pollingMessage));
        //System.out.println("判一次题时间:"+(end-start));
    }


}
