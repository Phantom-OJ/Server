package sustech.edu.phantom.dboj.form.advanced;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.edu.phantom.dboj.entity.po.Group;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename GroupForm
 * @date 2020/12/10 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "<admin> 变更群组表单")
public class GroupForm {
    @ApiModelProperty(value = "modification mode", notes = "0 for modification, 1 for adding, 2 for delete")
    private Integer mode;// 0 为修改 1 为增加 2 为删除
    @ApiModelProperty(value = "group list")
    private List<Group> groups;
}
