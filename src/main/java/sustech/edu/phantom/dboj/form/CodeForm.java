package sustech.edu.phantom.dboj.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeForm {
    private Integer cid;
    private String code;
    private Timestamp submitTime;
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
}
