package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.UserForm;
import sustech.edu.phantom.dboj.form.modification.ModifyPasswdForm;
import sustech.edu.phantom.dboj.mapper.UserMapper;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
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

    public Object[] modifyPassword(ModifyPasswdForm form, String username) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userMapper.findUserByUsername(username);
        //默认user存在了
        Object[] a = new Object[2];
        try {
            boolean flg = encoder.matches(form.getOldPassword(), user.getPassword());
            if (flg) {
                int flag = userMapper.modifyPassword(username, encoder.encode(form.getNewPassword()));
                if (flag == 1) {
                    a[0] = "Successful modification";
                    a[1] = HttpStatus.OK;
                } else {
                    a[0] = "Modification fails";
                    a[1] = HttpStatus.NOT_FOUND;
                }
            } else {
                a[0] = "Passwords are not matched";
                a[1] = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            a[0] = "Internal Server Error";
            a[1] = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return a;
    }
}
