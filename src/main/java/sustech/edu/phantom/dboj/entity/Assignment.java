package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {
    private Integer id;
    private String title;
    private String description;
    private Long startTime;
    private Long endTime;
    private String status;
    private Integer fullScore;
    private String sampleDatabasePath;
//    private Boolean valid;
    private List<Group> groupList;
    private List<Problem> problemList;
}
