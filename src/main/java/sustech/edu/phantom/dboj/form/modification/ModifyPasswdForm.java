package sustech.edu.phantom.dboj.form.modification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 19:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "password modification form")
public class ModifyPasswdForm {
    @ApiModelProperty(value = "old password")
    private String oldPassword;
    @ApiModelProperty(value = "new password")
    private String newPassword;
}
