package com.opticalarc.secure_web_app.serviceImpl;

import com.opticalarc.secure_web_app.dto.ChangePassword;
import com.opticalarc.secure_web_app.entity.ForgotPassword;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.repository.ForgotPasswordRepository;
import com.opticalarc.secure_web_app.repository.UserRepository;
import com.opticalarc.secure_web_app.service.ForgotPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public ForgotPasswordServiceImpl(ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean verifyOTP(ForgotPassword forgotPassword, User user) {

        if (forgotPassword.getExpiryTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return false;
        }
        return true;
    }


    @Override
    public boolean changePassword(ChangePassword changePassword) {
        if (!Objects.equals(changePassword.getPassword(),changePassword.getRepeatPassword())){
            return false;
        }
        String encodedPassword = passwordEncoder.encode(changePassword.getPassword());
        userRepository.updatePassword(encodedPassword, changePassword.getEmail());
        return true;
    }
}
