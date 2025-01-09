package com.opticalarc.secure_web_app.service;

import com.opticalarc.secure_web_app.dto.ChangePassword;
import com.opticalarc.secure_web_app.entity.ForgotPassword;
import com.opticalarc.secure_web_app.entity.User;

public interface ForgotPasswordService {

    boolean verifyOTP(ForgotPassword forgotPassword, User user);

    boolean changePassword(ChangePassword changePassword);
}
