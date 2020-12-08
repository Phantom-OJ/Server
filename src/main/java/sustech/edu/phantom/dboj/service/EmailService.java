package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;


/**
 * @author Lori
 */
@Service
@Slf4j
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    private final static int codeLength = 6;

    public String sendVerifyCode(String toMail) {
        String verificationCode = null;
        try {
            String title = "Verification Code for Phantom DBOJ";
            String text = "Your verification code is ";
            String tail = ". The verification code is valid within 5 minutes.";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(toMail);
            message.setSubject(title);
            verificationCode = generateCode();
            message.setText(text + verificationCode + tail);
            mailSender.send(message);
            log.info("Verification code {} is sent to {} successfully.", verificationCode, toMail);
        } catch (Exception e) {
            log.error("send error," + e.getMessage());
        }
        return verificationCode;
    }

    private String generateCode() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new SecureRandom();
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            a.append(str.charAt(random.nextInt(str.length())));
        }
        return a.toString();
    }
}
