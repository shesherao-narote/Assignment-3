package com.opticalarc.secure_web_app.service;

import com.opticalarc.secure_web_app.entity.ForgotPassword;
import com.opticalarc.secure_web_app.entity.User;

public interface EmailService {

    String sendOTPOnEmail(User user, String email);

//    String sendLinkOnEmail(String to, String subject, String body);

}
