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
@ApiModel(description = "Code form")
public class CodeForm {
    @ApiModelProperty(value = "code")
    private String code;
    @ApiModelProperty(value = "SQL language")
    private String dialect;
    @ApiModelProperty(value = "the submitting timestamp")
    private Long submitTime;
}
