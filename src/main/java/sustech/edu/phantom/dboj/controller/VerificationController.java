package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.form.home.RegisterForm;
import sustech.edu.phantom.dboj.form.home.RstPwdForm;
import sustech.edu.phantom.dboj.form.modification.ModifyUsernameForm;
import sustech.edu.phantom.dboj.service.EmailService;
import sustech.edu.phantom.dboj.service.UserService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Lori
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class VerificationController {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    /**
     * 发送验证码
     * username: 邮箱
     * mode: 0 (注册) 1 (重置密码) 2 (更换邮箱)
     *
     * @param map json对象
     * @return 是否成功信息
     */
    @RequestMapping(value = "/sendvcode", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> sendVCode(@RequestBody Map<String, Object> map){
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
            ValueOperations forValue = redisTemplate.opsForValue();
            forValue.set(username, codeString);
            redisTemplate.expire(username, 5 * 60, TimeUnit.SECONDS);
            res = ResponseMsg.OK;
        } catch (Exception e) {
            res = ResponseMsg.INTERNAL_SERVER_ERROR;
            log.error("Some ERRORs happen in the internal server");
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).build(), res.getStatus());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<Object>> signup(@RequestBody RegisterForm registerForm) throws Exception {
        User user = userService.find(registerForm.getUsername());
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String validateCodeInRedis = forValue.get(registerForm.getUsername());
        log.info(registerForm.toString());
        if (user != null || !registerForm.getVerifyCode().equals(validateCodeInRedis)) {
            return new ResponseEntity<>(GlobalResponse.builder().msg("Register Fails").build(), HttpStatus.BAD_REQUEST);
        } else {
            userService.register(registerForm);
            return new ResponseEntity<>(GlobalResponse.builder().msg("Register success").build(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/rstpwd")
    public ResponseEntity<GlobalResponse<String>> resetPassword(@RequestBody RstPwdForm form) {
        GlobalResponse<String> g = GlobalResponse.<String>builder().build();
        User user = userService.find(form.getUsername());
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String validateCodeInRedis = forValue.get(form.getUsername());
        if (!form.getVCode().equals(validateCodeInRedis) || user != null) {
            return new ResponseEntity<>(GlobalResponse.<String>builder().msg("Register Fails").build(), HttpStatus.BAD_REQUEST);
        } else {
            boolean a = userService.resetPassword(form);
            if (a) {
                g.setMsg("Modify successfully.");
                return new ResponseEntity<>(g, HttpStatus.OK);
            } else {
                g.setMsg("Failure modification");
                return new ResponseEntity<>(g, HttpStatus.BAD_REQUEST);
            }
        }
    }


    @RequestMapping(value = "/rstusr", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> modifyUsername(@RequestBody ModifyUsernameForm form) {
        ResponseMsg res;
        User user = userService.find(form.getUsername());
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String validateCodeInRedis = forValue.get(form.getUsername());
        if (user != null) {
            res = ResponseMsg.USER_ALREADY_EXIST;
        } else {
            if (!form.getVerifyCode().equals(validateCodeInRedis)) {
                res = ResponseMsg.VERIFICATION_CODE_NOT_MATCHED;
            } else {
                //TODO:变更email
                try {
                    res = ResponseMsg.OK;
                } catch (Exception e) {
                    res = ResponseMsg.INTERNAL_SERVER_ERROR;
                }
            }
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

}
