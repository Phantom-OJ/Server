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
 * @filename UserGroupForm
 * @date 2020/12/10 16:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "<admin> 添加删除群组人员")
public class UserGroupForm {
    @ApiModelProperty(value = "modification mode", notes = "1 for adding, 0 for delete")
    private Integer mode;
    @ApiModelProperty(value = "group id")
    private Integer groupId;
    @ApiModelProperty(value = "user id")
    private Integer userId;
}
