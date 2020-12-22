package sustech.edu.phantom.dboj.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sustech.edu.phantom.dboj.basicJudge.*;
import sustech.edu.phantom.dboj.entity.po.*;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.home.CodeForm;
import sustech.edu.phantom.dboj.mapper.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JudgeService {

    Gson gson=new Gson();


    private final static int AC = 0;// accept
    private final static int SE = 1;// system error
    private final static int TLE = 2;// time limit exceed
    private final static int WA = 3;// wrong answer
    private final static int RE = 4;// runtime error
    private final static int CNE = 5;// 连接失败
    private final static int UE = 6;//未知错误
    private final static int MLE = 9;// memory limit exceed
    @Autowired
    TestService testService;

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

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    public static ArrayList<String> answerStringToArrayList(String textAnswer) {
        ArrayList<String> answer = new ArrayList<>();
        String[] rows = textAnswer.trim().split("\n");
        for (int i = 0; i < rows.length; i++) {
            String curentRow = rows[i].replace("\"", "").trim();
            answer.add(curentRow);
        }
        return answer;
    }

//    public Integer judgeCodeOutside(int problemId, CodeForm codeForm, int userId) {
//
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
     * @param problemId problem id
     */
    public Integer judgeCode(int problemId, CodeForm codeForm, int userId) {
        Problem problem = problemMapper.queryCurrentProblem(problemId,false);
        //FastLinux.createDatabase("12002");
        /*插入code表*/
        Code code = Code.builder()
                .code(codeForm.getCode())
                .codeLength(codeForm.getCode().getBytes(StandardCharsets.UTF_8).length)
                .submitTime(codeForm.getSubmitTime())
                .dialect(codeForm.getDialect())
                .build();
        System.out.println(code);
        codeMapper.saveCode(code);

        System.out.println("okk");
        testService.sendRecord(String.valueOf(code.getId()));
        System.out.println("ojbk");

        System.out.println(code.getId());
        //
        System.out.println(problemId);

        System.out.println(problem);

        ArrayList<JudgeInput> judgeInputList = new ArrayList<>();
        HashMap<String,Object> map=new HashMap<>();
        map.put("type",problem.getType());
        List<JudgePoint> judgePointList = judgePointMapper.getAllJudgePointsOfProblemWithDialect(problemId,codeForm.getDialect());
        /*
        * JudgePoint转化为JudgeInput*/
        for (JudgePoint j : judgePointList) {
            String answer = j.getAnswer();
            JudgeDatabase judgeDatabase=judgeDatabaseMapper.getJudgeDatabaseById(j.getJudgeDatabaseId());
            String dbPath = judgeDatabase.getDatabaseUrl();
            JudgeInput currentInput = JudgeInput.builder()
                    .JudgeDatabase(gson.fromJson(dbPath,HashMap.class))
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
        Record record =Record.builder()
                .codeId(code.getId())
                .userId(userId)
                .problemId(problem.getId())
                .codeLength(code.getCodeLength())
                .submitTime(code.getSubmitTime()).
                        score(0).
                        result("Pending").
                        space(100L).
                        time(0L)
                .dialect(code.getDialect()).
                        build();
        recordMapper.saveRecord(record);

         JudgeInputMessage message =JudgeInputMessage.builder()
                 .recordId(record.getId())
                .judgeInputs(judgeInputList)
                .codeId(code.getId())
                .problemId(problemId)
                 .userId(userId)
                 .dialect(code.getDialect())
                 .build();
        String currentInput=gson.toJson(message);
        System.out.println(redisTemplate.opsForList().leftPush("judgelist",currentInput));
        return record.getId();

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


    public PollingMessage getJudgeStatus(String recordId) {
        return testService.getRecordStatusbyCodeId(recordId);
    }
}





