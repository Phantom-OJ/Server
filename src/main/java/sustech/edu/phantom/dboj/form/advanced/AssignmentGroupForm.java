package sustech.edu.phantom.dboj.form.advanced;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename AssignmentGroupForm
 * @date 2020/12/10 16:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "<admin> 变更作业群组表单")
public class AssignmentGroupForm {
    @ApiModelProperty(value = "modification mode", notes = "1 for adding, 0 for delete")
    private Integer mode;
    @ApiModelProperty(value = "group id")
    private Integer groupId;
    @ApiModelProperty(value = "user id")
    private Integer assignmentId;
}
