package sustech.edu.phantom.dboj.entity.dto;

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
public class RecordProblem {
    private Integer judgePointIndex;
    private Integer time;
    private Integer space;
    private String result;
    private String description;
}
