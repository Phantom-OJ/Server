package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.UserForm;
import sustech.edu.phantom.dboj.form.modification.ModifyPasswdForm;
import sustech.edu.phantom.dboj.mapper.UserMapper;

/**
 * @author Lori
 */
@Service
@Slf4j
public class BasicInfoModificationService {

    @Autowired
    UserMapper userMapper;

    /**
     * 修改个人信息
     *
     * @param userForm 传进来的user对象，这里可能要改，重新弄个user form 出来
     * @return 修改过的个人信息
     */
    public User modifyPersonalInfo(UserForm userForm, int userId) throws Exception {
        boolean flag = userMapper.updateUserInfo(userForm, userId);
        log.info("update state is {}", flag);
        if (flag) {
            return userMapper.findUserById(userId);
        } else {
            log.error("error updating");
        }
        return userMapper.findUserById(userId);
    }

    public Object[] modifyPassword(ModifyPasswdForm form) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userMapper.findUserByUsername(form.getUsername());
        //默认user存在了
        boolean flg = encoder.matches(form.getOldPassword(), user.getPassword());
        Object[] a = new Object[2];
        if (flg) {
            int flag = userMapper.modifyPassword(form.getUsername(), encoder.encode(form.getNewPassword()));
            if (flag == 1) {
                a[0]= "Successful modification";
                a[1] = HttpStatus.OK;
            } else {
                a[0]= "Modification fails";
                a[1] = HttpStatus.NOT_FOUND;
            }
        } else {
            a[0]= "Passwords are not matched";
            a[1] = HttpStatus.NOT_FOUND;
        }
        return a;
    }
}
