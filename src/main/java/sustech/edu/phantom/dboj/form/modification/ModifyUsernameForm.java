package sustech.edu.phantom.dboj.form.modification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyUsernameForm {
    private String username;
    private String verifyCode;
}
