package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.UserForm;
import sustech.edu.phantom.dboj.form.modification.ModifyPasswdForm;
import sustech.edu.phantom.dboj.service.BasicInfoModificationService;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Lori
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class BasicModifyInfoController {

    @Autowired
    BasicInfoModificationService basicInfoModificationService;

    @RequestMapping(value = "/modifyInfo",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<User>> user(@RequestBody UserForm form, HttpServletRequest request) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.toString());
        ResponseMsg res;
        try {
            boolean flag = basicInfoModificationService.modifyPersonalInfo(form, user.getId());
            if (!flag) {
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            } else {
                SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                Authentication authentication = securityContextImpl.getAuthentication();
                //TODO:初始化UsernamePasswordAuthenticationToken实例 ，这里的参数user就是我们要更新的用户信息
                user.modifyInfo(form);//这里不知道对不对
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials());
                auth.setDetails(authentication.getDetails());
                securityContextImpl.setAuthentication(auth);
                User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.info(user1.toString());
                res = ResponseMsg.OK;
            }
        } catch (Exception e) {
            log.error("Some errors happen in the internal server.");
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<User>builder().msg(res.getMsg()).data(user).build(), res.getStatus());
//        try {
//            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (user.getUsername().equals(form.getUsername())) {
//                //TODO:user1 是更改过的用户信息
//                User user1 = basicInfoModificationService.modifyPersonalInfo(form, user.getId());
//                SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//                Authentication authentication = securityContextImpl.getAuthentication();
//                //TODO:初始化UsernamePasswordAuthenticationToken实例 ，这里的参数user就是我们要更新的用户信息
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials());
//                auth.setDetails(authentication.getDetails());
//                securityContextImpl.setAuthentication(auth);
//                //TODO: return
//                return new ResponseEntity<>(GlobalResponse.<User>builder().msg("Forbidden").data(user1).build(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(GlobalResponse.<User>builder().msg("Forbidden").data(null).build(), HttpStatus.FORBIDDEN);
//            }
//        } catch (ClassCastException e) {
//            log.error("You have not signed in.");
//            return new ResponseEntity<>(GlobalResponse.<User>builder().msg("Not authorized").data(null).build(), HttpStatus.UNAUTHORIZED);
//        }
    }

    @RequestMapping(value = "/modifypasswd", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> modifyPassword(@RequestBody ModifyPasswdForm form) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object[] a = basicInfoModificationService.modifyPassword(form, user.getUsername());
        //TODO: 更新现有user, 如果返回200则要强制退出
        return new ResponseEntity<>(
                GlobalResponse
                        .<String>builder()
                        .msg((String) a[0])
                        .build(), (HttpStatus) a[1]);
    }
}
