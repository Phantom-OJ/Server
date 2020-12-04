package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.Permission;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.service.AdvancedInfoModificationService;

import java.util.Map;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class AdvancedModifyInfoController {

    @Autowired
    AdvancedInfoModificationService advancedInfoModificationService;
    /**
     * 返回对应身份的权限信息
     *
     * @return
     */
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Permission>> getPermission() {

        //判断身份

        return new ResponseEntity<>(GlobalResponse.<Permission>builder().msg("Success").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyPermission(@AuthenticationPrincipal User user) {

        return new ResponseEntity<>(GlobalResponse.<String>builder().msg("success").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> grantOthers(@RequestBody Map<String, Object> hm) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("grant other users")) {
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").data(null).build(), HttpStatus.FORBIDDEN);
            } else {
                String msg;
                try {
                    advancedInfoModificationService.grantUser(hm);
                    msg = "success";
                } catch (Exception e) {
                    msg = "fail";
                }
                return new ResponseEntity<>(GlobalResponse.<String>builder().build(), "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ClassCastException e) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Not authorized").data(null).build(), HttpStatus.UNAUTHORIZED);
        }


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
