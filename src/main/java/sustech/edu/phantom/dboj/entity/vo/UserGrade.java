package sustech.edu.phantom.dboj.entity.vo;

import io.swagger.annotations.ApiModel;
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
    private Integer id;
    private String title;
    private Integer score;
    private Integer fullScore;
}
