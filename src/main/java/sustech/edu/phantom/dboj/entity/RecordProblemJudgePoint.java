package sustech.edu.phantom.dboj.entity;

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
public class RecordProblemJudgePoint {
    private transient Integer recordId;
    private transient Integer problemId;
    private Integer judgePointIndex;
    private Long time;
    private Long space;
    private String result;
    private String description;
}
