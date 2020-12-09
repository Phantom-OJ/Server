package sustech.edu.phantom.dboj.form.modification;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "User form for modifications")
public class UserForm {
    @ApiModelProperty(value = "nickname")
    private String nickname;
    @ApiModelProperty(value = "whether to save the state")
    private Boolean stateSave;
    @ApiModelProperty(value = "default language that the user use")
    private String lang;
}
