package sustech.edu.phantom.dboj.entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class CodeForm {
    private Integer cid;
    private String code;
    private Timestamp submitTime;
    private boolean valid;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getSubmitTime() {
        return submitTime.getTime();
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
