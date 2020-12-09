package sustech.edu.phantom.dboj.form.modification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "username modification form")
public class ModifyUsernameForm {
    @ApiModelProperty(value = "username")
    private String username;
    @ApiModelProperty(value = "verification code")
    private String verifyCode;
}
