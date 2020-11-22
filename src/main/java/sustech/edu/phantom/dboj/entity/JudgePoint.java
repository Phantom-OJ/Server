package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
