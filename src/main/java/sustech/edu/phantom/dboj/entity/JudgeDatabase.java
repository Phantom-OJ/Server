package sustech.edu.phantom.dboj.entity;

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
    private String databaseUrl;
}
