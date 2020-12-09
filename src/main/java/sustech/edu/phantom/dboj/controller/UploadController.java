package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:05
 */
@RestController
@Slf4j
@RequestMapping(value = "/api")
@Api(tags = "upload functions, may be discarded in the future")
//TODO: sampledb judgedb script avatar 图片链接
public class UploadController {

    @Autowired
    UploadService uploadService;

    /**
     * 上传公告
     *
     * @param form 公告表单
     * @return
     */
    @RequestMapping(value = "/upload/announcement", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAnnouncement(@RequestBody UploadAnnouncementForm form) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getPermissionList().contains("publish the announcement")) {
                String msg;
                try {
                    msg = uploadService.saveAnnouncement(form) ? "success" : "fail";
                } catch (Exception e) {
                    log.error(Arrays.toString(e.getStackTrace()));
                    msg = "fail";
                }
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg(msg).build(), "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").data(null).build(), HttpStatus.FORBIDDEN);
            }
        } catch (ClassCastException e) {
            log.error("You have not logged in.");
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Not authorized").data(null).build(), HttpStatus.UNAUTHORIZED);
        }
    }
//
//    //TODO:上传照片
//    @RequestMapping(value = "/upload/avatar", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
//    public ResponseEntity<GlobalResponse<String>> uploadAvatar(String path) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (user.containSomePermission("modify personal information")) {
//
//        } else {
//            return null;
//        }
//        return null;
//    }

    @RequestMapping(value = "/judgedb", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<JudgeDatabase>>> getAllJudgeDB(HttpServletRequest request) {
        ResponseMsg res;
        List<JudgeDatabase> data = new ArrayList<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("view judge database")) {
                res = ResponseMsg.FORBIDDEN;
            } else {
                //TODO: query judge database表 异常处理
                List<JudgeDatabase> list = uploadService.getAllJudgeDB();
                if (list == null) {
                    res = ResponseMsg.NOT_FOUND;
                } else {
                    res = ResponseMsg.OK;
                    data = list;
                }
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<List<JudgeDatabase>>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @RequestMapping(value = "/judgedb/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<JudgeDatabase>> selOneJDB(HttpServletRequest request, @PathVariable Integer id) {
        ResponseMsg res;
        JudgeDatabase data = null;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("view judge database")) {
                res = ResponseMsg.FORBIDDEN;
            } else {
                //TODO: query judge database表 异常处理
                JudgeDatabase j = uploadService.selOneDB(id);
                if (j == null) {
                    res = ResponseMsg.NOT_FOUND;
                } else {
                    res = ResponseMsg.OK;
                    data = j;
                }
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<JudgeDatabase>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @RequestMapping(value = "/judgescript", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<List<JudgeScript>>> getJudgeScript(HttpServletRequest request) {
        ResponseMsg res;
        List<JudgeScript> data = new ArrayList<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.containPermission(PermissionEnum.VIEW_JUDGE_DETAILS)) {
                res = ResponseMsg.FORBIDDEN;
            } else {
                List<JudgeScript> list = uploadService.getAllJudgeScript();
                if (list == null) {
                    res = ResponseMsg.NOT_FOUND;
                } else {
                    res = ResponseMsg.OK;
                    data = list;
                }
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<List<JudgeScript>>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

//    @RequestMapping(value = "/upload/judgescipt", method = RequestMethod.POST)
//    public ResponseEntity<GlobalResponse<String>> uploadJudgeScript(HttpServletRequest request, @RequestBody List<String> list) {
//        ResponseMsg res;
//        try {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (!user.getPermissionList().contains("upload judge script")) {
//                res = ResponseMsg.FORBIDDEN;
//
//            } else {
//                //TODO:插入judge script表
//                res = ResponseMsg.OK;
//                if (true) {
//
//                } else {
//
//                }
//            }
//        } catch (ClassCastException e) {
//            res = ResponseMsg.UNAUTHORIZED;
//            log401(request);
//        }
//        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
//    }

    @RequestMapping(value = "/judgescript/{id}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<JudgeScript>> selOneJs(HttpServletRequest request, @PathVariable Integer id) {
        ResponseMsg res;
        JudgeScript data = null;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("view judge script")) {
                res = ResponseMsg.FORBIDDEN;
            } else {
                //TODO: query judge database表 异常处理
                JudgeScript j = uploadService.selOneJScript(id);
                if (j == null) {
                    res = ResponseMsg.NOT_FOUND;
                } else {
                    res = ResponseMsg.OK;
                    data = j;
                }
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<JudgeScript>builder().msg(res.getMsg()).data(data).build(), res.getStatus());
    }

    @RequestMapping(value = "/upload/assignment", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAssignment(HttpServletRequest request, @RequestBody UploadAssignmentForm form) {
        ResponseMsg res;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.containPermission(PermissionEnum.CREATE_ASSIGNMENT)) {
                log.error("The visit from " + request.getRemoteAddr() + " has low authorities.");
                res = ResponseMsg.FORBIDDEN;
            }else{
                //TODO: 插入assignment, problem, judge_point表
                if (uploadService.saveAssignment(form)) {
                    res = ResponseMsg.OK;
                } else{
                    res = ResponseMsg.INTERNAL_SERVER_ERROR;
                }
            }
        } catch (ClassCastException e) {
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

    // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
    public static void log401(HttpServletRequest request) {
        log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
    }

    public static void log403(HttpServletRequest request) {
        log.error("The visit from " + request.getRemoteAddr() + " has low authentication.");
    }

    public static void log500(HttpServletRequest request) {
        log.error("The visit from " + request.getRemoteAddr() + " has internal server error.");
    }

}
