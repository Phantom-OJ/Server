package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.UserForm;
import sustech.edu.phantom.dboj.response.GlobalResponseMsg;
import sustech.edu.phantom.dboj.service.modification.ModifyUserInfoService;


/**
 * @author Lori
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ModifyInfoController {

    @Autowired
    ModifyUserInfoService modifyUserInfoService;

    @RequestMapping(value = "/modifyInfo")
    public User user(@AuthenticationPrincipal User user, @RequestBody UserForm userForm) throws Exception {
        User user1 = null;
        try {
            int id = user.getId();
            user1 = modifyUserInfoService.modifyPersonalInfo(userForm, id);
        } catch (NullPointerException e) {
            log.error("You have not signed in.");
        }

        return user1;
    }
}
