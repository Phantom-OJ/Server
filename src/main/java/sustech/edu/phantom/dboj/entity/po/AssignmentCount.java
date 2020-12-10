package sustech.edu.phantom.dboj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename AssignmentCount
 * @date 2020/12/11 01:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentCount {
    private Integer problemId;
    private Integer count;
}
