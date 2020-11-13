package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double spaceLimit;
    private Double timeLimit;
    private Integer numberSubmit;
    private Integer numberSolve;
    private Integer indexInAssignment;
    private String solution;
    private Boolean valid;
    private List<Tag> tagList;
}
