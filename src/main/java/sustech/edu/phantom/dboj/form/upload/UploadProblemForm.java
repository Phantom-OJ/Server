package sustech.edu.phantom.dboj.form.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Upload problem form")
public class UploadProblemForm {
    @ApiModelProperty(value = "the index of the problem")
    private Integer id;
    @ApiModelProperty(value = "the index of the problem in the assignment")
    private Integer indexInAssignment;
    @ApiModelProperty(value = "problem title")
    private String title;
    @ApiModelProperty(value = "problem description")
    private String description;
    @ApiModelProperty(value = "problem full score")
    private Integer fullScore;
    @ApiModelProperty(value = "problem space limit")
    private Long spaceLimit;
    @ApiModelProperty(value = "problem time limit")
    private Long timeLimit;
    @ApiModelProperty(value = "problem solution")
    private String solution;
    @ApiModelProperty(value = "problem tags")
    private List<Integer> tagList;
    @ApiModelProperty(value = "problem types", example = "select/trigger")
    private String type;
    @ApiModelProperty(value = "problem judge points")
    private List<UploadJudgePointForm> judgePointList;
}
