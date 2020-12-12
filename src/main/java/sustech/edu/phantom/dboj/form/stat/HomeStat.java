package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Home statistics")
public class HomeStat {
    @ApiModelProperty(value = "date")
    private String date;
    @ApiModelProperty(value = "the number of submission")
    private Integer count;
}
