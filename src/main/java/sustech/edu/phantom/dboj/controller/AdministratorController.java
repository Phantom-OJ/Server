package sustech.edu.phantom.dboj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.service.ProblemService;
import sustech.edu.phantom.dboj.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
/**
 * 这里所有的方法都得要权限
 */
public class AdministratorController {

    @Autowired
    UserService userService;

    @Autowired
    ProblemService problemService;
}
