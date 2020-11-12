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

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime.getTime();
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime.getTime();
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFullScore() {
        return fullScore;
    }

    public void setFullScore(Integer fullScore) {
        this.fullScore = fullScore;
    }

    public String getSampleDatabasePath() {
        return sampleDatabasePath;
    }

    public void setSampleDatabasePath(String sampleDatabasePath) {
        this.sampleDatabasePath = sampleDatabasePath;
    }
}
