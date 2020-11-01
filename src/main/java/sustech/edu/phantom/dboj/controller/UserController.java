package sustech.edu.phantom.dboj.controller;

import com.google.gson.Gson;

import sustech.edu.phantom.dboj.entity.*;
import sustech.edu.phantom.dboj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/api/user/signup", method = RequestMethod.POST)
    public User signup(@RequestBody RegisterForm registerForm) {
        return userService.register(registerForm);
    }

    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm);
    }

    @RequestMapping(value = "api/user/announcement", method = RequestMethod.POST)
    public List<Announcement> getAnnouncement(@RequestBody AnnouncementQuery announcementQuery) {
//        List<Announcement> a = userService.announcementList(announcementQuery);
        return userService.announcementList(announcementQuery);
    }

    @RequestMapping(value = "api/user/announcementlist", method = RequestMethod.POST)
    public int insertAnnouncementList(@RequestBody List<Announcement> announcementList) {
        return userService.insertAnnouncementList(announcementList);
    }

    @RequestMapping(value = "api/user/code", method = RequestMethod.POST)
    public int insertCode(@RequestBody CodeForm codeForm) {
        return 1;
    }
}
