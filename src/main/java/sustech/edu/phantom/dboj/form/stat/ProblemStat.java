package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Lori
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Problem statistics")
public class ProblemStat {
    @ApiModelProperty(value = "the key value")
    private String key;
    @ApiModelProperty(value = "the count number")
    private Integer count;
}
