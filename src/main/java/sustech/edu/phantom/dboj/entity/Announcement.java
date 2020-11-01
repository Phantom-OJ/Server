package sustech.edu.phantom.dboj.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Announcement {
    private Integer ancmtId;
    private String title;
    private String description;
    private Timestamp createDate;
    private Timestamp lastModified;
    private boolean valid;


    public Integer getAncmtId() {
        return ancmtId;
    }

    public void setAncmtId(Integer ancmtId) {
        this.ancmtId = ancmtId;
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

    public long getCreateDate() {
        return createDate.getTime();
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public long getLastModified() {
        return lastModified.getTime();
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
