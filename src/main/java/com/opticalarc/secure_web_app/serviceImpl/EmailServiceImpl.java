package com.opticalarc.secure_web_app.serviceImpl;

import com.opticalarc.secure_web_app.dto.MailBody;
import com.opticalarc.secure_web_app.dto.OTPGenerator;
import com.opticalarc.secure_web_app.entity.ForgotPassword;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.repository.ForgotPasswordRepository;
import com.opticalarc.secure_web_app.service.EmailService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.properties.from}")
    private String from;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public String sendEmail(User user, String email) {

        Integer otp = OTPGenerator.generateOTP();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP for forgot password");
        message.setText("OTP : " + otp + " for forgot password request");
        message.setFrom(from);
        javaMailSender.send(message);

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expiryTime(new Date(System.currentTimeMillis()+ 70 * 1000))
                .user(user)
                .build();

        forgotPasswordRepository.save(forgotPassword);
        return "Email sent for verification";
    }

}
