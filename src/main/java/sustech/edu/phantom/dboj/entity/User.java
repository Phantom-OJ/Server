package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private Integer groupId;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String avatar;
    private Boolean stateSave;
    private String state;
    private String lang;
//    private Boolean valid;
    private List<Permission> permissionList;
    private List<Group> groupList;
}
