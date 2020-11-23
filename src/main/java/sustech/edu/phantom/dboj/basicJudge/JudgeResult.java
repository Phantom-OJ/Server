package sustech.edu.phantom.dboj.basicJudge;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JudgeResult {
    Integer code;//0为成功，1为语法错误，2为超时，3是wrong answer,4是运行时错误,5是数据库连接错误,6是判题机未知错误
    Long runTime;
    String codeDescription;
    ArrayList<String> userAnswer;//执行结果，是一个以“|”分隔列的table，每一行是一个元素
    HashMap<String,Object> additionFields;


    public static JudgeResult ANSWER_CORRECT=new JudgeResult(0,"Answer Correct");
    public static JudgeResult SYNTAX_ERROR=new JudgeResult(1,"Systax Error");
    public static JudgeResult TIME_LIMIT_EXCEED=new JudgeResult(2,"Time Limit Exceed");
    public static JudgeResult WRONG_ANSWER=new JudgeResult(3,"Wrong Answer");
    public static JudgeResult RUN_TIME_ERROR=new JudgeResult(4,"Runtime error");
    public static JudgeResult CONNECTION_ERROR=new JudgeResult(5,"Failed to connect to given database");
    public static JudgeResult UNKNOWN_ERROR=new JudgeResult(6,"未知错误，估计是判题机出bug了ORZ");
    public static JudgeResult METHOD_ERROR=new JudgeResult(7,"TYPE类型不支持");
    public static JudgeResult FORMAT_ERROR=new JudgeResult(8,"报文格式不正确");

    @Override
    public String toString() {
        return "JudgeResult{" +
                "code=" + code +
                ", runTime=" + runTime +
                ", codeDescription='" + codeDescription + '\'' +
                ", userAnswer=" + userAnswer +
                ", additionFields=" + additionFields +
                '}';
    }

    public JudgeResult(Integer code, String codeDescription) {
        this.runTime=0L;
        this.code = code;
        this.codeDescription = codeDescription;
    }

    public JudgeResult() {
    }

    public Integer getCode() {
        return code;
    }


    public String getCodeDescription() {
        return codeDescription;
    }



    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public ArrayList<String> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(ArrayList<String> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }


}
