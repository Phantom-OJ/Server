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
@ApiModel(description = "grade")
public class Grade {
    @ApiModelProperty(value = "user id")
    private Integer userId;
    @ApiModelProperty(value = "problem id")
    private Integer problemId;
    @ApiModelProperty(value = "score")
    private Integer score;
}
