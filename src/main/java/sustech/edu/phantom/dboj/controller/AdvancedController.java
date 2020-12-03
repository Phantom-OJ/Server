package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Permission;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class AdvancedController {

    /**
     * 返回对应身份的权限信息
     * @return
     */
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse<Permission>> getPermission() {
        //判断身份

        return new ResponseEntity<>(GlobalResponse.<Permission>builder().msg("Success").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> modifyPermission(@AuthenticationPrincipal User user) {

        return new ResponseEntity<>(GlobalResponse.<String>builder().msg("success").build(), HttpStatus.OK);
    }
}
