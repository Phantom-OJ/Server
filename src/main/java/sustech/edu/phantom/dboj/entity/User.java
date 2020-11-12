package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer uid;
    private Integer gid;
    private String username;
    private String password;
    private String nickname;
    private char role;
    private String avatar;
    private boolean stateSave;
    private String state;
    private String lang;

//    public User(RegisterForm registerForm) {
//        this.username = registerForm.getUsername();
//        this.password = registerForm.getPassword();
//        this.nickname = registerForm.getNickname();
//    }

//    public User(LoginForm loginForm) {
//        this.username = loginForm.getUsername();
//        this.password = loginForm.getPassword();
//    }
}
