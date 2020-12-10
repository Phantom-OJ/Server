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
 * @date 2020/12/4 02:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Upload assignment form")
public class UploadAssignmentForm {
    @ApiModelProperty(value = "assignment title")
    private String title;
    @ApiModelProperty(value = "assignment description")
    private String description;
    @ApiModelProperty(value = "assignment start time")
    private Long startTime;
    @ApiModelProperty(value = "assignment end time")
    private Long endTime;
    @ApiModelProperty(value = "assignment status")
    private String status;
    @ApiModelProperty(value = "total score of the assignment")
    private Integer fullScore;
    @ApiModelProperty(value = "assignment sample database path")
    private String sampleDatabasePath;
    @ApiModelProperty(value = "assignment problem list")
    private List<UploadProblemForm> uploadProblemFormList;
    @ApiModelProperty(value = "assignment groups")
    private List<Integer> groupList;
}
