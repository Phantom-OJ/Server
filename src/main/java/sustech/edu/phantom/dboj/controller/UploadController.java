package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;

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
        return null;
    }

    @RequestMapping(value = "/assignment", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAssignment(@AuthenticationPrincipal User user, @RequestBody Announcement a) {
        return null;
    }

}
