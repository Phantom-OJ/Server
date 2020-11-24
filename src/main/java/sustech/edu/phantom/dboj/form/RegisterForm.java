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
    @ApiModelProperty(value = "username")
    private String username;
    @ApiModelProperty(value = "password")
    private String password;
    @ApiModelProperty(value = "nickname")
    private String nickname;
    @ApiModelProperty(value = "verification code")
    private String vCode;
}
