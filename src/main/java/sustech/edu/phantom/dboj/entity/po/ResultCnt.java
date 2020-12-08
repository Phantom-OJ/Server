package sustech.edu.phantom.dboj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/8 19:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultCnt {
    private String result;
    private Integer count;
}
