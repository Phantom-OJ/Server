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
    private String code;
    private Timestamp submitTime;

    public long getSubmitTime() {
        return submitTime.getTime();
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = new Timestamp(submitTime);
    }
}
