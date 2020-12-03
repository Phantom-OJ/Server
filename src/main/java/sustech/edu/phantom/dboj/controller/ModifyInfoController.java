package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;
import sustech.edu.phantom.dboj.form.UserForm;
import sustech.edu.phantom.dboj.form.modification.ModifyPasswdForm;
import sustech.edu.phantom.dboj.service.InfoModificationService;

import java.util.Map;


/**
 * @author Lori
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ModifyInfoController {

    @Autowired
    InfoModificationService infoModificationService;

    @RequestMapping(value = "/modifyInfo")
    public User user(@AuthenticationPrincipal User user, @RequestBody UserForm form) throws Exception {
        User user1 = null;
        try {
            int id = user.getId();
            user1 = infoModificationService.modifyPersonalInfo(form, id);
        } catch (NullPointerException e) {
            log.error("You have not signed in.");
        }

        return user1;
    }

    @RequestMapping(value = "/modifypasswd", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyPassword(@AuthenticationPrincipal User user, @RequestBody ModifyPasswdForm form) {
        if (!user.getUsername().equals(form.getUsername())) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
        } else {
            Object[] a = infoModificationService.modifyPassword(form);
            return new ResponseEntity<>(
                    GlobalResponse
                            .<String>builder()
                            .msg((String) a[0])
                            .build(), (HttpStatus) a[1]);
        }
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> grantOthers(@AuthenticationPrincipal User user, @RequestBody Map<String, String> hm) {
        if (user == null) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("You have not logged in").build(), HttpStatus.UNAUTHORIZED);
        }
        if (!user.getPermissionList().contains("grant other users")) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
        }
        String msg;
        try {
            //TODO: 修改权限信息
            msg = "success";
        } catch (Exception e) {
            msg = "fail";
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().build(), "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //对题目进行修改
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyProblem(@PathVariable Integer id, @AuthenticationPrincipal User u, @RequestBody Problem p) {

        if (u == null) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("You have not logged in").build(), HttpStatus.UNAUTHORIZED);
        }
        if (!u.getPermissionList().contains("modify problem")) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
        }
        String msg;
        try {
            //TODO: 修改题目信息
            msg = "success";
        } catch (Exception e) {
            msg = "fail";
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().build(), "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //对作业进行修改
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyAssign(@PathVariable Integer id, @AuthenticationPrincipal User u, @RequestBody Assignment a) {
        if (u == null) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("You have not logged in").build(), HttpStatus.UNAUTHORIZED);
        }
        if (!u.getPermissionList().contains("modify assignment")) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().build(), HttpStatus.FORBIDDEN);
        }
        String msg;
        try {
            //TODO: 修改作业信息
            msg = "success";
        } catch (Exception e) {
            msg = "fail";
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().build(), "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
