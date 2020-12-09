package sustech.edu.phantom.dboj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lori
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JudgeDatabase {
    private Integer id;
    private String keyword;
    private String databaseUrl;
}
