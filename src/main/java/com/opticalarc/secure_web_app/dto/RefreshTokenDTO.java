package com.opticalarc.secure_web_app.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RefreshTokenDTO {

    private Long id;

    private String refreshToken;

    private Instant expiryDate;

    private UserDTO user;
}
