package sustech.edu.phantom.dboj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.service.UserService;

@RestController
@RequestMapping("/sa/api")
public class SAController {
    @Autowired
    UserService userService;

}
