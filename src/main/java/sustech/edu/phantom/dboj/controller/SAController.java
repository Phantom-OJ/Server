package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.service.UserService;

@RestController
@RequestMapping("/api/sa")
@Slf4j
public class SAController {
    @Autowired
    UserService userService;

}
