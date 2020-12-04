package sustech.edu.phantom.dboj.form.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadProblemForm {
    private Integer indexInAssignment;
    private String title;
    private String description;
    private Integer fullScore;
    private Long spaceLimit;
    private Long timeLimit;
    private String solution;
    private List<Integer> tagList;
    private String type;
}
