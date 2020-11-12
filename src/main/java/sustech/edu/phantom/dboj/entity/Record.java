package sustech.edu.phantom.dboj.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data

public class Record {
    private Integer rid;
    private Integer cid;// code id
    private Integer uid;
    private Integer pid;
    private String result;
    private String description; // ?
    private Double space;
    private Double time;
    private String dialect;
    private Integer codeLength;
    private Timestamp submitTime;

    public long getSubmitTime() {
        return submitTime.getTime();
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = new Timestamp(submitTime);
    }
}
