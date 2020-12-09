package sustech.edu.phantom.dboj.form.upload;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Upload assignment form")
public class UploadAssignmentForm {
    private String title;
    private String description;
    private Long startTime;
    private Long endTime;
    private String status;
    private Integer fullScore;
    private String sampleDatabasePath;
    private List<UploadProblemForm> uploadProblemFormList;
    private List<Integer> groupList;
}
