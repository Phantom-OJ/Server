package sustech.edu.phantom.dboj.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sustech.edu.phantom.dboj.entity.User;

@Controller
@RequestMapping(value = "/api")
public class BeaconController {
    @RequestMapping(value = "/beacon", method = RequestMethod.POST)
    public String beacon() throws Exception {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            System.out.println(principal.getUsername());
            return "true";
        } else {
            System.out.println("no user");
            return "false";
        }
    }
}
