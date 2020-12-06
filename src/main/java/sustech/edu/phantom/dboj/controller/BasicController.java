package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class BasicController {
    @RequestMapping(value = "/checkstate", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<User>> checkState(HttpServletRequest request){
        User user = null;
        ResponseMsg res;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            res = ResponseMsg.OK;
        } catch (ClassCastException e) {
            log.error("The request from " + request.getRemoteAddr() + " client has not been logged in.");
            res = ResponseMsg.UNAUTHORIZED;
        }
        return new ResponseEntity<>(GlobalResponse.<User>builder().msg(res.getMsg()).data(user).build(), res.getStatus());
    }
}
