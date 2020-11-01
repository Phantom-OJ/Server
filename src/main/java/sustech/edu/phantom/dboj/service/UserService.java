package sustech.edu.phantom.dboj.service;

import sustech.edu.phantom.dboj.entity.*;
import sustech.edu.phantom.dboj.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AnnouncementMapper announcementMapper;


    public User register(RegisterForm registerForm) {
        User user = new User(registerForm);
        int flag = userMapper.findByUsername(user.getUsername());
        if (flag > 0) {
            return null;
        } else {
            int flg = userMapper.registerUser(user);
            if (flg == 0) {
                return null;
            } else {
                return userMapper.loginUser(user);
            }
        }
    }

    public User login(LoginForm loginForm) {
        User user = new User(loginForm);
        return userMapper.loginUser(user);
    }

    public List<Announcement> announcementList(AnnouncementQuery announcementQuery) {
        announcementQuery.setParameters();
        return announcementMapper.queryAnnouncement(announcementQuery);
    }
    public int insertAnnouncementList(List<Announcement> announcementList){
        return announcementMapper.insertAnnouncement(announcementList);
    }
}
