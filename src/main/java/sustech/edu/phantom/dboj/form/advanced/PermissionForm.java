package sustech.edu.phantom.dboj.form.advanced;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.edu.phantom.dboj.entity.po.Permission;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "<admin> for changing the contents of permissions")
public class PermissionForm {
    @ApiModelProperty(value = "modification mode", notes = "1 for adding, 0 for delete")
    private Integer mode;// 0 为修改 1 为增加 2 为删除
    @ApiModelProperty(value = "permission list")
    private Permission permission;
}
