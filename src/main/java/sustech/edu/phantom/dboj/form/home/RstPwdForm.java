package sustech.edu.phantom.dboj.form.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/25 18:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RstPwdForm {
    private String username;
    private String newPassword;
    private String verifyCode;
}
