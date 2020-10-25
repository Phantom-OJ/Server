package sustech.edu.phantom.dboj.service;

import sustech.edu.phantom.dboj.entity.*;
import sustech.edu.phantom.dboj.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UsersService {
    @Autowired
    UsersMapper usersMapper;

    public Users registerAccount(Users users) {
        int flag = usersMapper.registerUser(users);
        if (flag > 0) {
            return usersMapper.findUserByusername(users.getUsername());
        }else{
            return null;
        }
    }
}
