package sustech.edu.phantom.dboj.form.home;

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
@ApiModel(description = "Login form")
public class LoginForm {
    @ApiModelProperty(value = "username")
    private String username;
    @ApiModelProperty(value = "password")
    private String password;
}
