package sustech.edu.phantom.dboj.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lori
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "assignment")
public class Assignment {
    @ApiModelProperty(value = "assignment id")
    private Integer id;
    @ApiModelProperty(value = "assignment title")
    private String title;
    @ApiModelProperty(value = "assignment description")
    private String description;
    @ApiModelProperty(value = "timestamp that assignment starts", example = "1606143958177")
    private Long startTime;
    @ApiModelProperty(value = "timestamp that assignment ends", example = "1606143958177")
    private Long endTime;
    @ApiModelProperty(value = "assignment status", example = "public")
    private String status;
    @ApiModelProperty(value = "assignment full score", example = "100")
    private Integer fullScore;
    @ApiModelProperty(value = "assignment title",example = "jdbc:postgresql://localhost:5432/xxxxx")
    private String sampleDatabasePath;
    @ApiModelProperty(value = "groups that one assignment belongs to")
    private List<Group> groupList;
    @ApiModelProperty(value = "problems that one assignment has")
    private List<Problem> problemList;
}
