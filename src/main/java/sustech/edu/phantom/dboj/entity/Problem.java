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
    private Integer pid;
    private String title;
    private String description;
    private Integer fullScore;
    private Double spaceLimit;
    private Double timeLimit;
    private Integer numberSubmit;
    private Integer numberSolve;
    private Integer indexInAssignment;
    private String solution;
    private List<String> tags;
}
