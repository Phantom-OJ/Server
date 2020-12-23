package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.*;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.search.GroupRoleForm;
import sustech.edu.phantom.dboj.service.GroupService;
import sustech.edu.phantom.dboj.service.RequireService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:05
 */
@RestController
@RequestMapping(value = "/api/require")
@Slf4j
@Api(tags = {"Request for group/tag info"})
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class RequireController {

    @Autowired
    GroupService groupService;

    @Autowired
    RequireService requireService;

    @ApiOperation("获取标签信息")
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<Tag>>> getTags(
            HttpServletRequest request
    ){
        ResponseMsg msg;
        List<Tag> data = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.VIEW_TAGS)){
            try {
                data = requireService.getTag();
                msg = ResponseMsg.OK;
            } catch (Exception e) {
                log.error("Errors happen in the group list");
                msg = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        } else {
            msg = ResponseMsg.FORBIDDEN;
            log.error("Forbidden from request " + request.getRemoteAddr() + " when visiting tags.");
        }
        return new ResponseEntity<>(GlobalResponse.<List<Tag>>builder().data(data).msg(msg.getMsg()).build(), msg.getStatus());
    }

    @ApiOperation("获取分组信息")
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<Group>>> getGroups(
            HttpServletRequest request
    ) {
        ResponseMsg msg;
        List<Group> data = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.VIEW_GROUPS)) {
            try {
                data = groupService.getAllGroups();
                msg = ResponseMsg.OK;
            } catch (Exception e) {
                log.error("Errors happen in the group list");
                msg = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        } else {
            msg = ResponseMsg.FORBIDDEN;
            log.error("Forbidden from request " + request.getRemoteAddr() + " when visiting groups.");
        }
        return new ResponseEntity<>(GlobalResponse.<List<Group>>builder().data(data).msg(msg.getMsg()).build(), msg.getStatus());
    }

//
//    @ApiOperation("获取一个群组的所有人员")
//    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
//    public ResponseEntity<GlobalResponse<List<User>>> getUserByGroup(
//            HttpServletRequest request,
//            @PathVariable
//            @ApiParam(name = "群组id", value = "java.lang.Integer", required = true) Integer id){
//        ResponseMsg res;
//        List<User> users = null;
//        try {
//            users = requireService.getUsersByGroup(id);
//            res = ResponseMsg.OK;
//        } catch (Exception e) {
//            log.error("There are some errors happening when visiting from " + request.getRemoteAddr());
//            res = ResponseMsg.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<>(GlobalResponse.<List<User>>builder().msg(res.getMsg()).data(users).build(), res.getStatus());
//    }


//    @ApiOperation("获取不在某个群组的所有人")
//    @RequestMapping(value = "/notgroup/{id}", method = RequestMethod.GET)
//    public ResponseEntity<GlobalResponse<List<User>>> getUserNotInGroup(
//            HttpServletRequest request,
//            @PathVariable
//            @ApiParam(name = "群组id", value = "java.lang.Integer", required = true) Integer id){
//        ResponseMsg res;
//        List<User> users = null;
//        try {
//            users = requireService.getUserNotInGroup(id);
//            res = ResponseMsg.OK;
//        } catch (Exception e) {
//            log.error("There are some errors happening when visiting from " + request.getRemoteAddr());
//            res = ResponseMsg.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<>(GlobalResponse.<List<User>>builder().msg(res.getMsg()).data(users).build(), res.getStatus());
//    }


//    @ApiOperation("获取所有用户信息")
//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    public ResponseEntity<GlobalResponse<List<User>>> getUsers(
//            HttpServletRequest request,
//            @RequestBody @ApiParam(name = "过滤器", value = "java.lang.String", required = true) String filter
//    ) {
//        ResponseMsg res;
//        List<User> users = null;
//        try {
//            users = requireService.getUsers(filter);
//            res = ResponseMsg.OK;
//        } catch (Exception e) {
//            log.error("There are some errors happening when visiting from " + request.getRemoteAddr());
//            res = ResponseMsg.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<>(GlobalResponse.<List<User>>builder().msg(res.getMsg()).data(users).build(), res.getStatus());
//    }

    @ApiOperation("获取全部判题数据库")
    @RequestMapping(value = "/judgedb", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<JudgeDatabase>>> getAllJudgeDB(
            HttpServletRequest request) {
        ResponseMsg res;
        List<JudgeDatabase> data = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                List<JudgeDatabase> list = requireService.getAllJudgeDB();
                res = ResponseMsg.OK;
                data = list;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge database from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<JudgeDatabase>>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @ApiOperation("获取单一判题数据库")
    @RequestMapping(value = "/judgedb/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<JudgeDatabase>> selOneJDB(
            HttpServletRequest request,
            @PathVariable
            @ApiParam(name = "判题数据库id", value = "java.lang.Integer", required = true) Integer id) {
        ResponseMsg res;
        JudgeDatabase data = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                JudgeDatabase j = requireService.selOneDB(id);
                res = ResponseMsg.OK;
                data = j;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge database from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<JudgeDatabase>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }


    @ApiOperation("获取全部判题脚本")
    @RequestMapping(value = "/judgescript", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<JudgeScript>>> getJudgeScript(
            HttpServletRequest request) {
        ResponseMsg res;
        List<JudgeScript> data = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                List<JudgeScript> list = requireService.getAllJudgeScript();
                res = ResponseMsg.OK;
                data = list;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge script from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<JudgeScript>>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @ApiOperation("获取单一判题脚本")
    @RequestMapping(value = "/judgescript/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<JudgeScript>> selOneJs(
            HttpServletRequest request,
            @PathVariable
            @ApiParam(name = "脚本id", value = "java.lang.Integer", required = true) Integer id) {
        ResponseMsg res;
        JudgeScript data = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                JudgeScript j = requireService.selOneJScript(id);
                res = ResponseMsg.OK;
                data = j;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge script from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<JudgeScript>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }


    @ApiOperation("获取权限信息")
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<Permission>>> getPermission() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        List<Permission> permissionList = null;
        if (!user.containPermission(PermissionEnum.VIEW_PERMISSIONS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            permissionList = requireService.getPermissionList();
            res = ResponseMsg.OK;
        }
        return new ResponseEntity<>(GlobalResponse.<List<Permission>>builder().msg(res.getMsg()).data(permissionList).build(), res.getStatus());
    }

    @ApiOperation("获取人员接口")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<List<User>>> getUserInfo(
            HttpServletRequest request,
            @RequestBody @ApiParam(name = "筛选过滤器", value = "GroupRoleForm", required = true) GroupRoleForm groupRoleForm) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        List<User> users = null;
        if (!user.containPermission(PermissionEnum.VIEW_GROUPS)) {
            res = ResponseMsg.FORBIDDEN;
            log.error("Low privilege from the request");
        } else {
            try {
                users = requireService.getUserByFilter(groupRoleForm);
                res = ResponseMsg.OK;
            } catch (Exception e) {
                log.error("Internal server error from request " + request.getRemoteAddr());
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<User>>builder().msg(res.getMsg()).data(users).build(), res.getStatus());
    }

}
