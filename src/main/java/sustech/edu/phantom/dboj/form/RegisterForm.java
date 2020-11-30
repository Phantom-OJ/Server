package sustech.edu.phantom.dboj.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lori
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "register form")
public class RegisterForm {
    @ApiModelProperty(name = "username")
    private String username;
    @ApiModelProperty(name = "password")
    private String password;
    @ApiModelProperty(name = "nickname")
    private String nickname;
    @ApiModelProperty(name = "verification code")
    private String verifyCode;
}
