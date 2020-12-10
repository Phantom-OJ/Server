package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename AssignmentStat
 * @date 2020/12/9 20:06
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "assignment statistics")
public class AssignmentStat {
    @ApiModelProperty(value = "problem id")
    private Integer problemId;
    @ApiModelProperty(value = "problem title")
    private String problemTitle;
    @ApiModelProperty(value = "The number of students who pass the problem")
    private Integer ac;
    @ApiModelProperty(value = "The number of students who fail to pass the problem")
    private Integer wa;
    @ApiModelProperty(value = "The number of students who have not submitted the problem")
    private Integer noSubmission;
    @ApiModelProperty(value = "The number of students who need to submit the problem")
    private Integer total;

    public void setNoSubmission() {
        this.noSubmission = this.total - this.ac - this.wa;
    }
}
