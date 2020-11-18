package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {
    private Integer userId;
    private Integer problemId;
    private Integer score;
//    private Boolean valid;
}
