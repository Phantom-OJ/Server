package sustech.edu.phantom.dboj.form.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Upload judge point form")
public class UploadJudgePointForm {
    @ApiModelProperty(value = "problem id")
    private Integer problemId;
    @ApiModelProperty(value = "before SQL")
    private String beforeSql;
    @ApiModelProperty(value = "after SQL")
    private String afterSql;
    @ApiModelProperty(value = "judge script id")
    private Integer judgeScriptId;
    @ApiModelProperty(value = "answer")
    private String answer;
    @ApiModelProperty(value = "judge database id")
    private Integer judgeDatabaseId;
    @ApiModelProperty(value = "dialect")
    private String dialect;
}
