package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {
    private Integer id;
    private String title;
    private String description;
    private Long createDate;
    private Long lastModified;
    private Boolean valid;
}
