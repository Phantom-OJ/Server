package sustech.edu.phantom.dboj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Lori
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "announcement")
public class Announcement {
    @ApiModelProperty(value = "announcement id")
    private Integer id;
    @ApiModelProperty(value = "announcement title")
    private String title;
    @ApiModelProperty(value = "announcement description")
    private String description;
    @ApiModelProperty(value = "timestamp that announcement was created")
    private Long createDate;
    @ApiModelProperty(value = "timestamp that announcement was recently modified")
    private Long lastModified;
}
