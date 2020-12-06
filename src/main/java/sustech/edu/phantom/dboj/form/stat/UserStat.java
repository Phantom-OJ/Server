package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStat {
    @ApiModelProperty(value = "the key value")
    private String key;
    @ApiModelProperty(value = "the count number")
    private Integer count;
}
