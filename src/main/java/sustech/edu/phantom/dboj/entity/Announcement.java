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
    private Integer ancmtId;
    private String title;
    private String description;
    private Timestamp createDate;
    private Timestamp lastModified;


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

}
