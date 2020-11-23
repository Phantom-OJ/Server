package sustech.edu.phantom.dboj.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lori
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "User form for modifications")
public class UserForm {
    @ApiModelProperty(value = "nickname")
    private String nickname;
    @ApiModelProperty(value = "password")
    private String password;
    @ApiModelProperty(value = "avatar")
    private String avatar;
    @ApiModelProperty(value = "whether to save the state")
    private Boolean stateSave;
    @ApiModelProperty(value = "default language that the user use")
    private String lang;
}
