package sustech.edu.phantom.dboj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * @author Lori
 */
@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;


    /**
     * 这个就是打印日志的
     */

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public void sendVerifyCode(String toMail, int verificationCode) throws Exception {
        try {
            String title = "Verification Code for Phantom DBOJ";
            String text = "Your verification code is ";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(toMail);
            message.setSubject(title);
            message.setText(text + verificationCode);
            mailSender.send(message);
            logger.info("Sending successfully.");
        } catch (Exception e) {
            throw new Exception("send error," + e.getMessage());
        }
    }

    public boolean verifyCode(String inputCode, String verificationCode) {
        return verificationCode.equals(inputCode);
    }
}
