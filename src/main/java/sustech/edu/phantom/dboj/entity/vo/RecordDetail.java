package sustech.edu.phantom.dboj.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.edu.phantom.dboj.entity.po.RecordProblemJudgePoint;

import java.util.List;

/**
 * @author Lori
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDetail {
    private Integer id;
    private Integer codeId;
    private Integer userId;
    private Integer problemId;
    private Integer score;
    private String result;
    private List<RecordProblemJudgePoint> description;
    private Long space;
    private Long time;
    private String dialect;
    private Integer codeLength;
    private Long submitTime;
    private String problemTitle;
    private String avatar;
    private String username;
    private String code;
}
