package com.opticalarc.secure_web_app.service;

import com.opticalarc.secure_web_app.dto.RefreshTokenDTO;

import java.util.Optional;

public interface RefreshTokenService {

    public RefreshTokenDTO createRefreshToken(String username);

    public RefreshTokenDTO verifyRefreshToken(String refreshToken) throws Exception;

}
