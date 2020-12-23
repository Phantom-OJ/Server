package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.home.RegisterForm;
import sustech.edu.phantom.dboj.form.home.RstPwdForm;
import sustech.edu.phantom.dboj.form.modification.ModifyPasswdForm;
import sustech.edu.phantom.dboj.form.modification.ModifyUsernameForm;
import sustech.edu.phantom.dboj.form.modification.UserForm;
import sustech.edu.phantom.dboj.service.AccountService;
import sustech.edu.phantom.dboj.service.EmailService;
import sustech.edu.phantom.dboj.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:05
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
@Api(tags = {"用户修改个人信息/注册"})
public class AccountController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    /**
     * 发送验证码
     * username: 邮箱
     * mode: 0 (注册) 1 (重置密码) 2 (更换邮箱)
     *
     * @param map json对象
     * @return 是否成功信息
     */
    @ApiOperation("发送验证码")
    @RequestMapping(value = "/sendvcode", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> sendVCode(
            @RequestBody
            @ApiParam(name = "用户名", value = "json", required = true)
                    Map<String, Object> map) {
        ResponseMsg res;
        String username = (String) map.get("username");
        int mode = (int) map.get("mode");
        User user = userService.find(username);
        try {
            if ((mode == 0 && user != null) || (mode == 2 && user != null)) {
                res = ResponseMsg.USER_ALREADY_EXIST;
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
            }
            if (mode == 1 && user == null) {
                res = ResponseMsg.USER_NOT_EXIST;
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
            }
            String codeString = emailService.sendVerifyCode(username);
            redisTemplate.opsForValue().set(username, codeString, 5 * 60, TimeUnit.SECONDS);
            res = ResponseMsg.OK;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
//            log.error("Some ERRORs happen in the internal server");
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> signup(
            HttpServletRequest request,
            @RequestBody
            @ApiParam(name = "注册表单", value = "json", required = true)
                    RegisterForm registerForm) {
        ResponseMsg res;
        try {
            User user = userService.find(registerForm.getUsername());
            String validateCodeInRedis = (String) redisTemplate.opsForValue().get(registerForm.getUsername());
            log.info(registerForm.toString());
            if (user != null || !registerForm.getVerifyCode().equals(validateCodeInRedis)) {
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Register Fails").build(), HttpStatus.BAD_REQUEST);
            } else {
                userService.register(registerForm);
                redisTemplate.opsForSet().pop(registerForm.getUsername());//弹出验证码
                return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Register success").build(), HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage() + " from the request of " + request.getRemoteAddr());
            res = ResponseMsg.REGISTER_FAIL;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
//            log.error("Some errors happen in the request of " + request.getRemoteAddr());
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("重置密码")
    @RequestMapping(value = "/rstpwd", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> resetPassword(
            HttpServletRequest request,
            @RequestBody
            @ApiParam(name = "重置密码表单", value = "json", required = true)
                    RstPwdForm form) {
        ResponseMsg res;
        try {
            User user = userService.find(form.getUsername());
            String validateCodeInRedis = (String) redisTemplate.opsForValue().get(form.getUsername());
            if (!form.getVerifyCode().equals(validateCodeInRedis)) {
                res = ResponseMsg.VERIFICATION_CODE_NOT_MATCHED;
//                log.error("Reset password: verification code not matched from " + request.getRemoteAddr());
            } else {
                if (user == null) {
                    res = ResponseMsg.USER_NOT_EXIST;
                    log.error("User not exists from " + request.getRemoteAddr());
                } else {
                    if (userService.resetPassword(form)) {
                        redisTemplate.opsForSet().pop(form.getUsername());//删除验证码
                        log.info("Modify " + form.getUsername() + " password from " + request.getRemoteAddr());
                        res = ResponseMsg.OK;
                    } else {
                        res = ResponseMsg.FAIL;
                        log.error("Fail to modify " + form.getUsername() + " password from " + request.getRemoteAddr());
                    }
                }
            }
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @ApiOperation("重置用户邮箱//not used")
    @RequestMapping(value = "/rstusr", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> modifyUsername(
            @RequestBody
            @ApiParam(name = "重置用户名表单", value = "json", required = true)
                    ModifyUsernameForm form) {
        return null;
    }

    /**
     * 修改个人信息
     * 修改nickname, state save 和  language
     *
     * @param form    修改信息的表单
     * @param request http request
     * @return 修改过后的个人信息
     */
    @ApiOperation("修改个人信息")
    @RequestMapping(value = "/modify/basic", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<User>> user(
            @RequestBody
            @ApiParam(name = "修改个人信息的表单", value = "json", required = true)
                    UserForm form,
            HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.toString());
        ResponseMsg res;
        try {
            boolean flag = accountService.modifyPersonalInfo(form, user.getId());
            if (!flag) {
                res = ResponseMsg.FAIL_MODIFY;
            } else {
                user.modifyInfo(form);
                res = ResponseMsg.OK;
                log.info("Modification of user " + user.getUsername() + " from " + request.getRemoteAddr() + " successfully");
            }
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(GlobalResponse.<User>builder().msg(res.getMsg()).data(user).build(), res.getStatus());
    }

    /**
     * <p>更改密码</p>
     *
     * @param form 修改密码的表单
     * @return 成功与否的信息
     */
    @ApiOperation("修改密码")
    @RequestMapping(value = "/modify/password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> modifyPassword(
            @RequestBody
            @ApiParam(name = "修改密码的表单", value = "json", required = true)
                    ModifyPasswdForm form) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object[] a = accountService.modifyPassword(form, user.getUsername());
        return new ResponseEntity<>(
                GlobalResponse
                        .<String>builder()
                        .msg((String) a[0])
                        .build(), (HttpStatus) a[1]);
    }

}
