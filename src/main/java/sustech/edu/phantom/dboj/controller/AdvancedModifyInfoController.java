package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.Assignment;
import sustech.edu.phantom.dboj.entity.po.Permission;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.po.User;
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
@Api(tags = {"<admin>高级修改信息"})
@RequestMapping(value = "/api/v1/modify/")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class AdvancedModifyInfoController {

    @Autowired
    AdvancedInfoModificationService advancedInfoModificationService;

    /**
     * 管理员在添加修改权限的时候获取权限信息
     *
     * @return 权限信息
     */
    @ApiOperation("获取权限信息")
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

    @ApiOperation("修改权限信息")
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyPermission(HttpServletRequest request) {

        return new ResponseEntity<>(GlobalResponse.<String>builder().msg("success").build(), HttpStatus.OK);
    }

    /**
     * 修改用户权限信息<br></br>
     * 管理员才有权限
     *
     * @param hm {"ROLE_xxx":[1,2,3],"ROLE_XXX":[4,5,6]}
     * @return 是否成功信息
     */
    @ApiOperation("修改用户的角色")
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> grantOthers(HttpServletRequest request, @RequestBody Map<String, List<Integer>> hm) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.GRANT)) {
            log.error("The visit from " + request.getRemoteAddr() + " has not such privilege.");
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                advancedInfoModificationService.grantUser(hm);
                res = ResponseMsg.OK;
            } catch (Exception e) {
                log.error("There are some errors of the visit from " + request.getRemoteAddr());
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("修改题目")
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
    @ApiOperation("修改作业")
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

    @ApiOperation("修改公告")
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
