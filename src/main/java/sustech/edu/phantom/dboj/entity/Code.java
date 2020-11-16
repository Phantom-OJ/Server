package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Code {
    private Integer id;
    private String code;
    private Integer codeLength;
    private Long submitTime;
    private Boolean valid;
}
