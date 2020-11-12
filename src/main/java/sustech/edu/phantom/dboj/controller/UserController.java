package sustech.edu.phantom.dboj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.LoginForm;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.form.RegisterForm;
import sustech.edu.phantom.dboj.service.ProblemService;
import sustech.edu.phantom.dboj.service.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ProblemService problemService;

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public User signup(@RequestBody RegisterForm registerForm) {
        return userService.register(registerForm);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm);
    }

    @RequestMapping(value = "/user/announcement", method = RequestMethod.POST)
    public List<Announcement> getAnnouncement(@RequestBody Pagination pagination) {
//        List<Announcement> a = userService.announcementList(pagination);
        return userService.announcementList(pagination);
    }



    @RequestMapping(value = "/problemlist", method = RequestMethod.POST)
    public List<Problem> getProblemList(@RequestBody Pagination pagination) {
        return problemService.getProblemPage(pagination);
    }
}
