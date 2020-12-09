package sustech.edu.phantom.dboj.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename UserGrade
 * @date 2020/12/9 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User grade form")
public class UserGrade {
    @ApiModelProperty(value = "assignment id")
    private Integer id;
    @ApiModelProperty(value = "assignment title")
    private String title;
    @ApiModelProperty(value = "assignment score got")
    private Integer score;
    @ApiModelProperty(value = "assignment total score")
    private Integer fullScore;
}
