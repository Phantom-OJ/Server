package sustech.edu.phantom.dboj.basicJudge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeInput implements Serializable {
    String JudgeDatabase;

    //String judgeDatabaseUrl;
    String userName;
    String passWord;
    String beforeInput;

    //DML
    String userInput;
    //DDL
    String afterInput;
    String standardAnswer;
    Long timeLimit;
    HashMap<String,Object> additionFields;


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
