package sustech.edu.phantom.dboj.form.modification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 19:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyPasswdForm {
    private String oldPassword;
    private String newPassword;
}
