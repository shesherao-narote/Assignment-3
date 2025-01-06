package com.opticalarc.secure_web_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response object for send meaningful and understandable message with API response.")
public class ApiResponse {

    private String message;
    private boolean success;

    public ApiResponse(String message, boolean success) {
        this.message=message;
        this.success=success;
    }
}
