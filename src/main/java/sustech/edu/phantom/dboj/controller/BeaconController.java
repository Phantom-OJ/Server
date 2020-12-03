package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.service.UserService;

@Controller
@RequestMapping(value = "/api")
@Slf4j
public class BeaconController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/beacon", method = RequestMethod.POST)
    public void beacon(String state) {
        User user;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getStateSave()){
                userService.saveState(state,user.getId());
                log.info("The state of "+ user.getUsername() +" has been saved into database.");
            } else {
                log.info("The state of "+ user.getUsername() +" has not been saved into database.");
            }
        } catch (ClassCastException e) {
            log.error("The account has not been logged in.");
        }
    }
}
