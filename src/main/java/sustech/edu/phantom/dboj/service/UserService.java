package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Permission;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.LoginForm;
import sustech.edu.phantom.dboj.form.RegisterForm;
import sustech.edu.phantom.dboj.form.home.RstPwdForm;
import sustech.edu.phantom.dboj.form.modification.ModifyPasswdForm;
import sustech.edu.phantom.dboj.mapper.GroupMapper;
import sustech.edu.phantom.dboj.mapper.PermissionMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.List;

/**
 * @author Lori
 * @date
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    GroupMapper groupMapper;

    public User register(RegisterForm registerForm) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder().
                username(registerForm.getUsername()).
                nickname(registerForm.getNickname()).
                password(encoder.encode(registerForm.getPassword())).build();
        User flag = userMapper.findUserByUsername(user.getUsername());
        if (flag != null) {
            throw new Exception("The username has been registered.");
        } else {
            boolean flg = userMapper.register(user);
            if (!flg) {
                throw new Exception("Your registration has been declined.");
            } else {
                User usr = userMapper.login(user);
                usr.setPermissionList(permissionMapper.getUserPermission(usr.getRole()));
                usr.setGroupList(groupMapper.getStudentGroup(usr.getId()));
                return usr;
            }
        }
    }

    public User login(LoginForm loginForm) {
        User user = User.builder().username(loginForm.getUsername()).password(loginForm.getPassword()).build();
        return userMapper.login(user);
    }

    public User find(String username) {
        return userMapper.findUserByUsername(username);
    }

    public Boolean resetPassword(RstPwdForm form) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userMapper.resetPassword(form.getUsername(), encoder.encode(form.getNewPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username does not exist");
        }
        user.setPermissionList(permissionMapper.getUserPermission(user.getRole()));
        return user;
    }
}
