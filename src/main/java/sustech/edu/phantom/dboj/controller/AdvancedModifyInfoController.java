package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.JudgePoint;
import sustech.edu.phantom.dboj.entity.po.Permission;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadAssignmentForm;
import sustech.edu.phantom.dboj.form.upload.UploadProblemForm;
import sustech.edu.phantom.dboj.service.AdvancedInfoModificationService;
import sustech.edu.phantom.dboj.service.GroupService;

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
@RequestMapping(value = "/api/modify/")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class AdvancedModifyInfoController {

    @Autowired
    AdvancedInfoModificationService advancedInfoModificationService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    GroupService groupService;

    @ApiOperation("添加权限信息")
    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    public ResponseEntity<GlobalResponse<String>> addPermission(
            HttpServletRequest request,
            @RequestBody
            @ApiParam(name = "要修改的权限信息", required = true, type = "permission")
                    Permission permission) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.VIEW_PERMISSIONS)) {
            if (advancedInfoModificationService.modifyPermission(permission, false, 0) > 0) {
                res = ResponseMsg.OK;
            } else {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
            log.error("Low privilege from the request " + request.getRemoteAddr());
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("删除权限信息")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GlobalResponse<String>> deletePermission(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "权限id", value = "java.lang.Integer", required = true)
                    Integer id) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.VIEW_PERMISSIONS)) {
            if (advancedInfoModificationService.modifyPermission(null, true, id) > 0) {
                res = ResponseMsg.OK;
            } else {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
            log.error("Low privilege from the request " + request.getRemoteAddr());
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
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
    public ResponseEntity<GlobalResponse<String>> grantOthers(
            HttpServletRequest request,
            @RequestBody
            @ApiParam(name = "授权信息", value = "map", required = true)
                    Map<String, List<Integer>> hm) {
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

    @ApiOperation("添加群组")
    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public ResponseEntity<GlobalResponse<String>> modifyGroup(
            HttpServletRequest request,
            @RequestBody
            @ApiParam(name = "群组描述", required = true, type = "java.lang.String")
                    String description
    ) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.CREATE_GROUPS)) {
            log.error("The visit from " + request.getRemoteAddr() + " has not such privilege.");
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (advancedInfoModificationService.modifyGroup(description, false, 0) > 0) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.BAD_REQUEST;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal error happens in the request from " + request.getRemoteAddr());
            }

        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("删除群组")
    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GlobalResponse<String>> deleteGroup(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "群组id", required = true, type = "java.lang.Integer")
                    Integer id) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.CREATE_GROUPS)) {
            log.error("The visit from " + request.getRemoteAddr() + " has not such privilege.");
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (advancedInfoModificationService.modifyGroup(null, true, id) > 0) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.BAD_REQUEST;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal error happens in the request from " + request.getRemoteAddr());
            }

        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("修改题目")
    @RequestMapping(value = "/problem/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyProblem(
            @PathVariable @ApiParam(name = "问题id", required = true, type = "java.lang.Integer") Integer id,
            @RequestBody @ApiParam(name = "问题", required = true, type = "Problem类") UploadProblemForm form) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (!user.containPermission(PermissionEnum.MODIFY_ASSIGNMENT)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (advancedInfoModificationService.modifyProblem(id, form)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL_MODIFY;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    //对作业进行修改
    @ApiOperation("修改作业")
    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyAssignment(
            @PathVariable @ApiParam(name = "作业id", required = true, type = "java.lang.Integer") Integer id,
            @RequestBody @ApiParam(name = "作业", required = true, type = "Assignment类") UploadAssignmentForm form) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (!user.containPermission(PermissionEnum.MODIFY_ASSIGNMENT)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (advancedInfoModificationService.modifyAssignment(id, form)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL_MODIFY;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("修改公告")
    @RequestMapping(value = "/announcement/{id}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyAnnouncement(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "公告id", required = true, type = "java.lang.Integer") Integer id,
            @RequestBody @ApiParam(name = "公告", required = true, type = "UploadAnnouncementForm") UploadAnnouncementForm form) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.CREATE_ANNOUNCEMENT)) {
            try {
                if (advancedInfoModificationService.modifyAnnouncement(form, id)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL_MODIFY;
                }
            } catch (Exception e) {
                log.error(e.getMessage() + " from request " + request.getRemoteAddr());
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("删除公告")
    @RequestMapping(value = "/announcement/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GlobalResponse<String>> deleteAnnouncement(
            HttpServletRequest request,
            @PathVariable @ApiParam(name = "公告id", required = true, type = "java.lang.Integer") Integer id) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.containPermission(PermissionEnum.CREATE_ANNOUNCEMENT)) {
            try {
                if (advancedInfoModificationService.deleteAnnouncement(id)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL_MODIFY;
                }
            } catch (Exception e) {
                log.error(e.getMessage() + " from request " + request.getRemoteAddr());
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("将用户添加到某群组")
    @RequestMapping(value = "/user_group/{uid}/{gid}", method = RequestMethod.PUT)
    public ResponseEntity<GlobalResponse<String>> addUser2Group(
            HttpServletRequest request,
            @PathVariable Integer uid, @PathVariable Integer gid) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (user.containPermission(PermissionEnum.CREATE_GROUPS)) {
            try {
                if (groupService.addOneUser2Group(uid, gid)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL_MODIFY;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("添加群组失败" + request.getRemoteAddr());
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("将用户从某群组删除")
    @RequestMapping(value = "/user_group/{uid}/{gid}", method = RequestMethod.DELETE)
    public ResponseEntity<GlobalResponse<String>> deleteUserFromGroup(
            HttpServletRequest request,
            @PathVariable Integer uid, @PathVariable Integer gid) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (user.containPermission(PermissionEnum.CREATE_GROUPS)) {
            try {
                if (groupService.deleteUserFromGroup(uid, gid)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.FAIL_MODIFY;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("删除群组失败" + request.getRemoteAddr());
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("添加判题点")
    @RequestMapping(value = "/judgepoint", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> putJudgePoint(
            HttpServletRequest request,
            @RequestBody @ApiParam(name = "判题点对象", required = true, type = "JudgePoint") JudgePoint judgePoint) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            if (advancedInfoModificationService.saveJudgePoint(judgePoint)) {
                res = ResponseMsg.OK;
            } else {
                res = ResponseMsg.FAIL_MODIFY;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }


    @ApiOperation("删除判题点")
    @RequestMapping(value = "/judgepoint/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> deleteJudgePoint(
            HttpServletRequest request, @PathVariable Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            if (advancedInfoModificationService.deleteJudgePoint(id)) {
                res = ResponseMsg.OK;
            } else {
                res = ResponseMsg.FAIL_MODIFY;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("修改判题点")
    @RequestMapping(value = "/judgepoint/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> modifyJudgePoint(
            HttpServletRequest request, @PathVariable Integer id,
            @RequestBody @ApiParam(name = "判题点对象", required = true, type = "JudgePoint") JudgePoint judgePoint) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseMsg res;
        if (user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            if (advancedInfoModificationService.modifyJudgePoint(id, judgePoint)) {
                res = ResponseMsg.OK;
            } else {
                res = ResponseMsg.FAIL_MODIFY;
            }
        } else {
            res = ResponseMsg.FORBIDDEN;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }
}
