package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.Permission;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.service.AdvancedInfoModificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/modify/")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class AdvancedModifyInfoController {

    @Autowired
    AdvancedInfoModificationService advancedInfoModificationService;

    /**
     * 返回对应身份的权限信息
     *
     * @return
     */
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<Permission>>> getPermission() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        List<Permission> permissionList = null;
        if (!user.containPermission(PermissionEnum.VIEW_PERMISSIONS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            //TODO:获取对应的权限信息
            res = ResponseMsg.OK;
        }

        return new ResponseEntity<>(GlobalResponse.<List<Permission>>builder().msg(res.getMsg()).data(permissionList).build(), res.getStatus());
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyPermission(HttpServletRequest request) {

        return new ResponseEntity<>(GlobalResponse.<String>builder().msg("success").build(), HttpStatus.OK);
    }

    /**
     * 修改用户权限信息<br></br>
     * 管理员才有权限
     *
     * @param hm
     * @return
     */
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> grantOthers(@RequestBody Map<String, Object> hm) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.GRANT)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {

            } catch (Exception e) {

            }
        }
        try {

            if (!user.containPermission(PermissionEnum.GRANT)) {
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
    public ResponseEntity<GlobalResponse<String>> modifyProblem(@PathVariable String id, @RequestBody Problem p) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        int idx;
        if (!user.containPermission(PermissionEnum.MODIFY_ASSIGNMENT)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                idx = Integer.parseInt(id);
                if (true) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL;
                }
            } catch (NumberFormatException e) {
                res = ResponseMsg.BAD_REQUEST;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    //对作业进行修改
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyAssign(@PathVariable String id, @RequestBody Assignment a) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        int idx;
        if (!user.containPermission(PermissionEnum.MODIFY_ASSIGNMENT)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                idx = Integer.parseInt(id);
                if (true) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL;
                }
            } catch (NumberFormatException e) {
                res = ResponseMsg.BAD_REQUEST;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyAnnouncement() {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.CREATE_ANNOUNCEMENT)) {

        } else {

        }
        return null;
    }
}
