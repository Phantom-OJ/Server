package sustech.edu.phantom.dboj.entity;

import lombok.Data;

@Data
public class Permission {
    private String role;
    private String allowance;
    private Boolean valid;
}
