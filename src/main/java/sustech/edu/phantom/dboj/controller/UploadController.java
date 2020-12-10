package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.enumeration.PermissionEnum;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.JudgeDatabase;
import sustech.edu.phantom.dboj.entity.po.JudgeScript;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadAssignmentForm;
import sustech.edu.phantom.dboj.service.UploadService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:05
 */
@RestController
@Slf4j
@RequestMapping(value = "/api")
@Api(tags = {"upload functions"})
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class UploadController {

    @Autowired
    UploadService uploadService;

    /**
     * 上传公告
     *
     * @param form 公告表单
     * @return 成功与否信息
     */
    @ApiOperation("上传公告")
    @RequestMapping(value = "/upload/announcement", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> uploadAnnouncement(
            HttpServletRequest request,
            @RequestBody UploadAnnouncementForm form) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.CREATE_ANNOUNCEMENT)) {
            res = ResponseMsg.FORBIDDEN;
            log.error("The request from " + request.getRemoteAddr() + " wants to create an announcement.");
        } else {
            try {
                if (uploadService.saveAnnouncement(form)) {
                    log.info("Successfully create an announcement from " + request.getRemoteAddr());
                    res = ResponseMsg.OK;
                } else {
                    log.error("Creating announcement fails " + request.getRemoteAddr());
                    res = ResponseMsg.FAIL;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when request from " + request.getRemoteAddr() + " wants to create announcement.");
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

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
                List<JudgeDatabase> list = uploadService.getAllJudgeDB();
                res = ResponseMsg.OK;
                data = list;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge database from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<JudgeDatabase>>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @ApiOperation("上传判题数据库")
    @RequestMapping(value = "/upload/judgescipt", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgeDB(
            HttpServletRequest request,
            @RequestBody JudgeDatabase judgeDatabase) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (uploadService.saveJudgeDB(judgeDatabase)) {
                    log.info("Successfully saving the judge database from " + request.getRemoteAddr());
                    res = ResponseMsg.OK;
                } else {
                    log.info("Not saving the judge database from " + request.getRemoteAddr());
                    res = ResponseMsg.FAIL;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when uploading judge database from request " + request.getRemoteAddr());
            }

        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("获取单一判题数据库")
    @RequestMapping(value = "/judgedb/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<JudgeDatabase>> selOneJDB(HttpServletRequest request, @PathVariable String id) {
        ResponseMsg res;
        JudgeDatabase data = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                int idx = Integer.parseInt(id);
                JudgeDatabase j = uploadService.selOneDB(idx);
                res = ResponseMsg.OK;
                data = j;
            } catch (NumberFormatException e) {
                res = ResponseMsg.NOT_FOUND;
                log.error("Wrong URL request from " + request.getRemoteAddr());
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge database from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<JudgeDatabase>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @ApiOperation("获取全部判题脚本")
    @RequestMapping(value = "/judgescript", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<JudgeScript>>> getJudgeScript(HttpServletRequest request) {
        ResponseMsg res;
        List<JudgeScript> data = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                List<JudgeScript> list = uploadService.getAllJudgeScript();
                res = ResponseMsg.OK;
                data = list;
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge script from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<List<JudgeScript>>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @ApiOperation("上传判题脚本")
    @RequestMapping(value = "/upload/judgescipt", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgeScript(
            HttpServletRequest request,
            @RequestBody JudgeScript judgeScript) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (uploadService.saveJudgeScript(judgeScript)) {
                    log.info("Successfully saving the judge script from " + request.getRemoteAddr());
                    res = ResponseMsg.OK;
                } else {
                    log.info("Not saving the judge script from " + request.getRemoteAddr());
                    res = ResponseMsg.FAIL;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when uploading judge script from request " + request.getRemoteAddr());
            }

        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("获取单一判题脚本")
    @RequestMapping(value = "/judgescript/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<JudgeScript>> selOneJs(HttpServletRequest request, @PathVariable String id) {
        ResponseMsg res;
        JudgeScript data = null;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                int idx = Integer.parseInt(id);
                JudgeScript j = uploadService.selOneJScript(idx);
                res = ResponseMsg.OK;
                data = j;
            } catch (NumberFormatException e) {
                res = ResponseMsg.NOT_FOUND;
                log.error("Wrong URL request from " + request.getRemoteAddr());
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when getting judge script from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<JudgeScript>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @ApiOperation("创建作业")
    @RequestMapping(value = "/upload/assignment", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAssignment(
            HttpServletRequest request,
            @RequestBody UploadAssignmentForm form) {
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.containPermission(PermissionEnum.CREATE_ASSIGNMENT)) {
            log.error("The visit from " + request.getRemoteAddr() + " has low authorities.");
            res = ResponseMsg.FORBIDDEN;
        } else {
            try {
                if (uploadService.saveAssignment(form)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.INTERNAL_SERVER_ERROR;
                }
            } catch (Exception e) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
                log.error("Internal server error when uploading assignment from request " + request.getRemoteAddr());
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }
}
