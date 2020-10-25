package sustech.edu.phantom.dboj.entity;
import java.sql.Timestamp;
import lombok.Data;
@Data
public class Announcement {
    private Integer ancmtId;
    private String text;
    private String description;
    private Timestamp createDate;
    private Timestamp lastModified;
    private boolean valid;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastModified() {
        return lastModified;
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
