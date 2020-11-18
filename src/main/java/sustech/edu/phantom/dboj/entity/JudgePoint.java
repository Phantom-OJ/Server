package sustech.edu.phantom.dboj.entity;

import lombok.Data;

@Data
public class JudgePoint {
    private Integer id;
    private Integer problemId;
    private String beforeSql;
    private String afterSql;
    private Integer judgeScriptId;
    private String answer;
    private Integer judgeDatabaseId;
//    private Boolean valid;
}
