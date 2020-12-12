package sustech.edu.phantom.dboj.form.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename GroupRoleForm
 * @date 2020/12/12 12:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRoleForm {
    private String username;
    private Integer group;
    private Integer notGroup;
    private String role;
    private String notRole;
}
