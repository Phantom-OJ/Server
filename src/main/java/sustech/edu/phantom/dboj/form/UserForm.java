package sustech.edu.phantom.dboj.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lori
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private String nickname;
    private String password;
    private String avatar;
    private Boolean stateSave;
    private String lang;
}
