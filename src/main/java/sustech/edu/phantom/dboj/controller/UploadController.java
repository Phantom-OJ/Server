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
import sustech.edu.phantom.dboj.entity.JudgePoint;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadAssignmentForm;
import sustech.edu.phantom.dboj.service.UploadService;

import java.util.Arrays;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:05
 */
@RestController
@Slf4j
@RequestMapping(value = "/api/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
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
    public ResponseEntity<GlobalResponse<EntityVO<JudgePoint>>> getAllJudgePoint() {
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

    @RequestMapping(value = "/judgepoint", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgePoint() {
        return null;
    }

    @RequestMapping(value = "/judgedb", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<String>> getAllJudgeDB() {
        //这里需要管理员权限
        try {

        } catch (ClassCastException e) {

        }
        return null;
    }

    @RequestMapping(value = "/judgedb", method = RequestMethod.POST)
    public void post(){

    }
    @RequestMapping(value = "/assignment", method = RequestMethod.POST)

    public ResponseEntity<GlobalResponse<String>> uploadAssignment(@RequestBody UploadAssignmentForm form) {

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

}
