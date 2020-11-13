package sustech.edu.phantom.dboj.entity;

import lombok.Data;

@Data
public class Code {
    private Integer id;
    private String code;
    private Integer codeLength;
    private Long submitTime;
    private Boolean valid;
}
