package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;

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

    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAnnouncement(@AuthenticationPrincipal User user, @RequestBody Announcement a) {
        if (user == null) {
            log.error("you dont logged in.");
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("you dont logged in.").build(), HttpStatus.UNAUTHORIZED);
        }
        if (!user.getPermissionList().contains("publish the announcement")) {
            log.error("authentication level low");
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
        } else {
            String msg;
            try {
                // TODO: 插入announcement表
                msg = "success";

            } catch (Exception e) {
                log.error(Arrays.toString(e.getStackTrace()));
                msg = "fail";
            }
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg(msg).build(), "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/assignment", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAssignment(@AuthenticationPrincipal User user, @RequestBody Assignment a) {
        if (user == null) {
            log.error("you dont logged in.");
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("you dont logged in.").build(), HttpStatus.UNAUTHORIZED);
        }
        if (!user.getPermissionList().contains("create assignment")) {
            log.error("authentication level low");
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Forbidden").build(), HttpStatus.FORBIDDEN);
        } else {
            String msg;
            try {
                // TODO: 插入assignment, problem, judge_point表 等等 要设计form
                msg = "success";

            } catch (Exception e) {
                log.error(Arrays.toString(e.getStackTrace()));
                msg = "fail";
            }
            return new ResponseEntity<>
                    (GlobalResponse.<String>builder()
                            .msg(msg)
                            .build(),
                            "success".equals(msg) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
