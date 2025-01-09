package com.opticalarc.secure_web_app.controller;

import com.opticalarc.secure_web_app.dto.ChangePassword;
import com.opticalarc.secure_web_app.dto.OTPEmailRequest;
import com.opticalarc.secure_web_app.entity.ForgotPassword;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.exception.ResourceNotFoundException;
import com.opticalarc.secure_web_app.repository.ForgotPasswordRepository;
import com.opticalarc.secure_web_app.repository.UserRepository;
import com.opticalarc.secure_web_app.service.EmailService;
import com.opticalarc.secure_web_app.service.ForgotPasswordService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, ForgotPasswordService forgotPasswordService){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/verify-email/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","Email",email));

        String status = emailService.sendEmail(user, email);
        return ResponseEntity.ok(status);
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPEmailRequest otpEmailRequest){
        User user = userRepository.findByEmail(otpEmailRequest.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User", "Email",otpEmailRequest.getEmail()));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otpEmailRequest.getOtp(),user)
                .orElseThrow(()->new ResourceNotFoundException("User", "OTP",otpEmailRequest.getOtp()));

        boolean status = forgotPasswordService.verifyOTP(forgotPassword,user);
        if (status) {
            return ResponseEntity.ok("OTP verified successfully");
        }
        return new ResponseEntity<>("OTP is expired", HttpStatus.EXPECTATION_FAILED);
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword){
        boolean status = forgotPasswordService.changePassword(changePassword);
        if (status){
            return ResponseEntity.ok("Password updated successfully");
        }
        return new ResponseEntity<>("Please enter the valid password",HttpStatus.EXPECTATION_FAILED);
    }

}
