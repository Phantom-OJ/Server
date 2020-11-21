package sustech.edu.phantom.dboj.service.modification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.mapper.UserMapper;

@Service
public class ModifyUserInfoService {

    @Autowired
    UserMapper userMapper;

    /**
     * 修改个人信息
     * @param user 传进来的user对象，这里可能要改，重新弄个user form 出来
     * @return 修改过的个人信息
     */
    public User modifyPersonalInfo(User user) {
        return null;
    }
}
