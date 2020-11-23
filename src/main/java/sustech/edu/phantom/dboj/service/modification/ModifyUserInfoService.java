package sustech.edu.phantom.dboj.service.modification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.UserForm;
import sustech.edu.phantom.dboj.mapper.UserMapper;

/**
 * @author Lori
 */
@Service
@Slf4j
public class ModifyUserInfoService {

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
}
