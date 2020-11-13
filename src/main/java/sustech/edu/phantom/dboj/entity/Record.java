package sustech.edu.phantom.dboj.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data

public class Record {
    private Integer id;
    private Integer codeId;
    private Integer userId;
    private Integer problemId;
    private Integer score;
    private String result;
    private String description;
    private Double space;
    private Double time;
    private String dialect;
    private Integer codeLength;
    private Long submitTime;
    private Boolean valid;
}
