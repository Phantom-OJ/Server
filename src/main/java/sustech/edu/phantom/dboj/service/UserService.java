package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.LoginForm;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.form.RegisterForm;
import sustech.edu.phantom.dboj.mapper.AnnouncementMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AnnouncementMapper announcementMapper;

    /**
     * 这里还差一个加密
     *
     * @param registerForm
     * @return
     */
    public User register(RegisterForm registerForm) {
        User user = User.builder().
                username(registerForm.getUsername()).
                nickname(registerForm.getNickname()).
                password(registerForm.getPassword()).build();
//        User user = new User(registerForm);
        int flag = userMapper.findUserByUsername(user.getUsername());
        if (flag > 0) {
            return null;
        } else {
            int flg = userMapper.register(user);
            if (flg == 0) {
                return null;
            } else {
                return userMapper.login(user);
            }
        }
    }

    public User login(LoginForm loginForm) {
        User user = User.builder().username(loginForm.getUsername()).password(loginForm.getPassword()).build();
//        User user = new User(loginForm);
        return userMapper.login(user);
    }

    public List<Announcement> announcementList(Pagination pagination) {
        pagination.setParameters();
        return announcementMapper.queryAnnouncement(pagination);
    }

    public int insertAnnouncementList(List<Announcement> announcementList) {
        return announcementMapper.insertAnnouncement(announcementList);
    }
}