package sustech.edu.phantom.dboj.basicJudge;

import java.io.Serializable;
import java.util.ArrayList;

public class JudgeInput implements Serializable {
    String JudgeDatabase;

    String beforeInput;

    //DML
    String userInput;
    //DDL
    String afterInput;
    ArrayList<String> standardAnswer;
    Long timeLimit;


    public JudgeInput() {
    }



    public String getBeforeInput() {
        return beforeInput;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getAfterInput() {
        return afterInput;
    }



    public Long getTimeLimit() {
        return timeLimit;
    }



    public void setBeforeInput(String beforeInput) {
        this.beforeInput = beforeInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public void setAfterInput(String afterInput) {
        this.afterInput = afterInput;
    }



    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = timeLimit;
    }




}
