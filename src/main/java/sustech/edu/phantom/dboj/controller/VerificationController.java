package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.vo.GlobalResponse;
import sustech.edu.phantom.dboj.form.RegisterForm;
import sustech.edu.phantom.dboj.form.home.RstPwdForm;
import sustech.edu.phantom.dboj.service.EmailService;
import sustech.edu.phantom.dboj.service.UserService;

import java.util.Map;
import java.util.Random;
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

    @RequestMapping(value = "/sendvcode", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<Object>> sendVCode(@RequestBody Map<String, Object> map) throws Exception {
        String username = (String) map.get("username");
        int mode = (int) map.get("mode");
        User user = userService.find(username);
        if (mode == 0 && user != null) {
            return new ResponseEntity<>(GlobalResponse.builder().msg("The account has been registered.").build(), HttpStatus.BAD_REQUEST);
        }
        if (mode == 1 && user == null) {
            return new ResponseEntity<>(GlobalResponse.builder().msg("The account does not exist.").build(), HttpStatus.BAD_REQUEST);
        }
        int code = new Random().nextInt(900000) + 100000;
        String codeString = code + "";
        emailService.sendVerifyCode(username, code);
        ValueOperations forValue = redisTemplate.opsForValue();
        forValue.set(username, codeString);
        redisTemplate.expire(username, 5 * 60, TimeUnit.SECONDS);
        return new ResponseEntity<>(GlobalResponse.builder().msg("Sending success!").build(), HttpStatus.OK);
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
}
