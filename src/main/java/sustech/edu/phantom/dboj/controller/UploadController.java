package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.JudgeDatabase;
import sustech.edu.phantom.dboj.entity.JudgePoint;
import sustech.edu.phantom.dboj.entity.JudgeScript;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadAssignmentForm;
import sustech.edu.phantom.dboj.form.upload.UploadJudgePointForm;
import sustech.edu.phantom.dboj.service.UploadService;

import javax.servlet.http.HttpServletRequest;
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
public class UploadController {

    @Autowired
    UploadService uploadService;

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

    @RequestMapping(value = "/judgepoint", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<EntityVO<JudgePoint>>> getAllJudgePoint(HttpServletRequest request) {
        //这里需要管理员权限
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getPermissionList().contains("View judge points")) {
                //TODO: 获取所有的judgepoints
                return new ResponseEntity<>(GlobalResponse.<EntityVO<JudgePoint>>builder().msg("success").build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(GlobalResponse.<EntityVO<JudgePoint>>builder().msg("Forbidden").data(null).build(), HttpStatus.FORBIDDEN);
            }
        } catch (ClassCastException e) {
            log.error("You have not logged in.");
            return new ResponseEntity<>(GlobalResponse.<EntityVO<JudgePoint>>builder().msg("Not authorized").data(null).build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/upload/judgepoint", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgePoint(HttpServletRequest request, @RequestBody UploadJudgePointForm form) {

        return null;
    }

    @RequestMapping(value = "/judgedb", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<EntityVO<JudgeDatabase>>> getAllJudgeDB(HttpServletRequest request) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("view judge database")) {
                return new ResponseEntity<>(GlobalResponse.<EntityVO<JudgeDatabase>>builder().msg("Forbidden").data(null).build(), HttpStatus.FORBIDDEN);
            } else {
                //TODO:插入judge database表 异常处理
                return new ResponseEntity<>(GlobalResponse.<EntityVO<JudgeDatabase>>builder().msg("Success").data(null).build(), HttpStatus.OK);
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            return new ResponseEntity<>(GlobalResponse.<EntityVO<JudgeDatabase>>builder().msg("Not authorized").data(null).build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/upload/judgedb", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgeDB(HttpServletRequest request, @RequestBody List<String> list) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("upload judge database")) {
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").data(null).build(), HttpStatus.FORBIDDEN);
            } else {
                //TODO:插入judge database表
                return null;
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Not authorized").data(null).build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/judgescript", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<EntityVO<JudgeScript>>> getJudgeScript(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/upload/judgescipt", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgeScript(HttpServletRequest request, @RequestBody List<String> list) {
        ResponseMsg res;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("upload judge script")) {
                res = ResponseMsg.FORBIDDEN;

            } else {
                //TODO:插入judge script表
                res = ResponseMsg.OK;
                if (true) {

                } else {

                }
            }
        } catch (ClassCastException e) {
            res = ResponseMsg.UNAUTHORIZED;
            log401(request);
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

    @RequestMapping(value = "/upload/assignment", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAssignment(HttpServletRequest request, @RequestBody UploadAssignmentForm form) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            log.error("You have not logged in.");
            return null;
        }

//        if (user == null) {
//            log.error("you dont logged in.");
//            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("you dont logged in.").build(), HttpStatus.UNAUTHORIZED);
//        }
//        if (!user.getPermissionList().contains("create assignment")) {
//            log.error("authentication level low");
//            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
//        } else {
//            String msg;
//            try {
//                // TODO: 插入assignment, problem, judge_point表 等等 要设计form
//                msg = "success";
//
//            } catch (Exception e) {
//                log.error(Arrays.toString(e.getStackTrace()));
//                msg = "fail";
//            }
//            return new ResponseEntity<>
//                    (GlobalResponse.<String>builder()
//                            .msg(msg)
//                            .build(),
//                            "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
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
