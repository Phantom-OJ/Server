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

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSpace() {
        return space;
    }

    public void setSpace(Double space) {
        this.space = space;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    public long getSubmitTime() {
        return submitTime.getTime();
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }
}
