package sustech.edu.phantom.dboj.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "code")
public class Code {
    @ApiModelProperty(value = "code id")
    private Integer id;
    @ApiModelProperty(value = "code")
    private String code;
    @ApiModelProperty(value = "code length")
    private Integer codeLength;
    @ApiModelProperty(value = "submission timestamp")
    private Long submitTime;
}
