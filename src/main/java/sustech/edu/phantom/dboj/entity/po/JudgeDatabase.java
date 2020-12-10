package sustech.edu.phantom.dboj.entity.po;

import io.swagger.annotations.Api;
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
@Api(value = "judge database entity")
public class JudgeDatabase {
    @ApiModelProperty(value = "judge database id")
    private Integer id;
    @ApiModelProperty(value = "judge database keyword")
    private String keyword;
    @ApiModelProperty(value = "judge database information")
    private String databaseUrl;
    @ApiModelProperty(value = "judge database dialect")
    private String dialect;
}
