package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sustech.edu.phantom.dboj.entity.enumeration.ProblemSolved;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Problem {
    private Integer id;
    private Integer assignmentId;
    private String title;
    private String description;
    private Integer fullScore;
    private Long spaceLimit;
    private Long timeLimit;
    private Integer numberSubmit;
    private Integer numberSolve;
    private Integer indexInAssignment;
    private String solution;
    private String status;
    private List<Tag> tagList;
    private String recentCode;
    private String type;
    private Integer solved;//0 for WA, 1 for AC, 2 for no submission

    public void setSolved(ProblemSolved p) {
        this.solved = p.getCode();
    }
}
