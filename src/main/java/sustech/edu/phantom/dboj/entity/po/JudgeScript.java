package sustech.edu.phantom.dboj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeScript {
    private Integer id;
    private String keyword;
    private String script;
}
