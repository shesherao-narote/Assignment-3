package com.opticalarc.secure_web_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for create new Jwt Token using Refresh Token.")
public class RefreshTokenRequest {

    private String refreshToken;
}
