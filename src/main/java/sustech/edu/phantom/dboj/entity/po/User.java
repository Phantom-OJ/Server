package sustech.edu.phantom.dboj.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.form.modification.UserForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lori
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String avatar;
    private Boolean stateSave;
    private String state;
    private String lang;
    private transient Boolean valid;
    private transient List<String> permissionList;
    private List<Group> groupList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return valid == null || valid;
    }

    /**
     * 判断是否有该权限
     *
     * @param permission permissionEnum字符串
     * @return 是否有权限
     */
    public boolean containPermission(PermissionEnum permission) {
        return this.permissionList.contains(permission.getDetail());
    }

    /**
     * 判断user是否在problem的group里面
     *
     * @param groupList group list
     * @return 是否
     */
    public boolean isInGroup(List<Integer> groupList) {
        List<Integer> groups = this.groupList.stream().map(Group::getId).collect(Collectors.toList());
        return !Collections.disjoint(groups, groupList);
    }

    /**
     * 将关键信息隐藏
     */
    public void hideInfo() {
        setRole(null);
        setState(null);
        setStateSave(null);
        setLang(null);
        setValid(null);
        setPermissionList(null);
        setGroupList(null);
    }

    /**
     * 更改信息
     *
     * @param userForm user变更表单
     */
    public void modifyInfo(UserForm userForm) {
        setNickname(userForm.getNickname());
        setLang(userForm.getLang());
        setStateSave(userForm.getStateSave());
    }
}
