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
public class Assignment {
    private Integer aid;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private Integer fullScore;
    private String sampleDatabasePath;

    public long getStartTime() {
        return startTime.getTime();
    }

    public void setStartTime(long startTime) {
        this.startTime = new Timestamp(startTime);
    }

    public long getEndTime() {
        return endTime.getTime();
    }

    public void setEndTime(long endTime) {
        this.endTime = new Timestamp(endTime);
    }
}
