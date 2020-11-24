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
import sustech.edu.phantom.dboj.service.EmailService;
import sustech.edu.phantom.dboj.service.UserService;

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

    @RequestMapping(value = "/sendvcode/{username}", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<Object>> sendVCode(@PathVariable String username) throws Exception {
        User user = userService.find(username);
        if (user != null) {
            return new ResponseEntity<>(GlobalResponse.builder().msg("The account has been registered.").build(), HttpStatus.BAD_REQUEST);
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
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String validateCodeInRedis = forValue.get(registerForm.getUsername());
        User user = new User();
        System.out.println(validateCodeInRedis);
        if (registerForm.getVCode().equals(validateCodeInRedis)) {
            userService.register(registerForm);
            return new ResponseEntity<>(GlobalResponse.builder().msg("Register success").build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(GlobalResponse.builder().msg("Register fails").build(), HttpStatus.BAD_REQUEST);
        }
    }
}
