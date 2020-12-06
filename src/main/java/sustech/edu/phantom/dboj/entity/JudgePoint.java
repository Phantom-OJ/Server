package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.edu.phantom.dboj.form.upload.UploadJudgePointForm;

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

    public JudgePoint(UploadJudgePointForm form) {
        beforeSql = form.getBeforeSql();
        afterSql = form.getAfterSql();
        judgeScriptId = form.getJudgeScriptId();
        answer = form.getAnswer();
        judgeDatabaseId = form.getJudgeDatabaseId();
    }
}
