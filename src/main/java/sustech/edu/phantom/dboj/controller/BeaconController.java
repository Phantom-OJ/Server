package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 非 rest controller
 *
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/6 22:02
 */
@Controller
@RequestMapping(value = "/api")
@Slf4j
public class BeaconController {
    @Autowired
    UserService userService;

    /**
     * 退出浏览器时发送当前状态
     *
     * @param request http request 请求
     */
    @RequestMapping(value = "/beacon", method = RequestMethod.POST)
    public void beacon(HttpServletRequest request) {
        try {
            String state = new String(request.getInputStream().readAllBytes());
            log.info("The current exiting state is " + state + " from " + request.getRemoteAddr());
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getStateSave()) {
                userService.saveState(state, user.getId());
                log.info("The state of " + user.getUsername() + " has been saved into database.");
            } else {
                log.info("The state of " + user.getUsername() + " has not been saved into database.");
            }
        } catch (IOException e) {
            log.error("Input stream is wrong from address " + request.getRemoteAddr());
        } catch (ClassCastException e) {
            log.error("The account has not been logged in from address " + request.getRemoteAddr());
        } catch (Exception e) {
            log.error("Something wrong with request from address " + request.getRemoteAddr());
        }
    }
}
