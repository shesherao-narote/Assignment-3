package com.opticalarc.secure_web_app.service;

import com.opticalarc.secure_web_app.entity.ForgotPassword;
import com.opticalarc.secure_web_app.entity.User;

public interface EmailService {

    String sendEmail(User user, String email);


}
