package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


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

    public void sendVerifyCode(String toMail, int verificationCode) throws Exception {
        try {
            String title = "Verification Code for Phantom DBOJ";
            String text = "Your verification code is ";
            String tail = ". The verification code is valid within 5 minutes.";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(toMail);
            message.setSubject(title);
            message.setText(text + verificationCode + tail);
            mailSender.send(message);
            log.info("Verification code {} is sent to {} successfully.", verificationCode, toMail);
        } catch (Exception e) {
            throw new Exception("send error," + e.getMessage());
        }
    }

    public boolean verifyCode(String inputCode, String verificationCode) {
        return verificationCode.equals(inputCode);
    }
}
