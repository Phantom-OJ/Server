package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "assignment score statistics for teacher")
public class AssignmentScore {
    private Integer id;
    private String username;
    private Integer score;
}
