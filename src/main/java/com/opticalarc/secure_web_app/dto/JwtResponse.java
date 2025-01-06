package com.opticalarc.secure_web_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@Schema(description = "Response object for send Jwt Token nad Refresh Token.")
public class JwtResponse {

    String jwtToken;

    String refreshToken;
}
