package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JudgeDatabase {
    private Integer id;
    private String databaseUrl;//改成judgeDatabaseUrl
//    private Boolean valid;
}
