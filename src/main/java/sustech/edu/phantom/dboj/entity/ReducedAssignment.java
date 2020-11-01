package sustech.edu.phantom.dboj.entity;

import java.sql.Timestamp;

public class ReducedAssignment {
    private Integer aid;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;

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
}
