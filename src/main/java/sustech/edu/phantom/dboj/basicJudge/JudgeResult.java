package sustech.edu.phantom.dboj.basicJudge;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class JudgeResult {
    Integer code;//0为成功，1为语法错误，2为超时，3是wrong answer 后续还可以拓展
    Long runTime;
    String codeDescription;
    ArrayList<String> userAnswer;//执行结果，是一个以“|”分隔列的table，每一行是一个元素
    String[] additionFields;

    @Override
    public String toString() {
        return "JudgeResult{" +
                "code=" + code +
                ", runTime=" + runTime +
                ", codeDescription='" + codeDescription + '\'' +
                ", userAnswer=" + userAnswer +
                ", additionFields=" + Arrays.toString(additionFields) +
                '}';
    }

    public JudgeResult() {
    }

    public Integer getCode() {
        return code;
    }


    public String getCodeDescription() {
        return codeDescription;
    }


    public String[] getAdditionFields() {
        return additionFields;
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


    public void setAdditionFields(String[] additionFields) {
        this.additionFields = additionFields;
    }
}
