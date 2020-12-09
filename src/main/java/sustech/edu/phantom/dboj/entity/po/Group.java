package sustech.edu.phantom.dboj.entity.po;

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
@ApiModel(description = "group")
public class Group {
    @ApiModelProperty(value = "group id")
    private Integer id;
    @ApiModelProperty(value = "group description")
    private String description;
}
