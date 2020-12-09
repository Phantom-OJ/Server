package sustech.edu.phantom.dboj.form.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Upload announcement form")
public class UploadAnnouncementForm {
    @ApiModelProperty(value = "announcement title")
    private String title;
    @ApiModelProperty(value = "announcement description")
    private String description;
    @ApiModelProperty(value = "timestamp that announcement was created")
    private Long createDate;
}
