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

    public long getCreateDate() {
        return createDate.getTime();
    }

    public void setCreateDate(long createDate) {
        this.createDate = new Timestamp(createDate);
    }

    public long getLastModified() {
        return lastModified.getTime();
    }

    public void setLastModified(long lastModified) {
        this.lastModified = new Timestamp(lastModified);
    }

}
