package sustech.edu.phantom.dboj.entity;

import lombok.Data;

@Data
public class JudgeDatabase {
    private Integer id;
    private String databasePath;
    private Boolean valid;
}
