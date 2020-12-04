package sustech.edu.phantom.dboj.form.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadJudgePointForm {
    private Integer problemId;
    private String beforeSql;
    private String afterSql;
    private Integer judgeScriptId;
    private String answer;
    private Integer judgeDatabaseId;
}
