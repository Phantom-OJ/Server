package sustech.edu.phantom.dboj.entity;

import lombok.Data;

@Data
public class Grade {
    private Integer userId;
    private Integer problemId;
    private Integer score;
    private Boolean valid;
}
