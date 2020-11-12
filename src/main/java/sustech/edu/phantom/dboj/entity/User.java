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
    private Boolean stateSave;
    private String state;
    private String lang;
    private Boolean valid;
}
