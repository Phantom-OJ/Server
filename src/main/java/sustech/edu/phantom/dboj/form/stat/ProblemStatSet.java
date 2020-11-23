package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lori
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "problem statistics set")
public class ProblemStatSet {
    @ApiModelProperty(value = "result statistics set", example = "<ac, 5>, <wa, 6>")
    List<ProblemStat> resultSet;
    @ApiModelProperty(value = "dialect statistics set", example = "<mysql, 5>, <pgsql, 6>")
    List<ProblemStat> dialectSet;
}
