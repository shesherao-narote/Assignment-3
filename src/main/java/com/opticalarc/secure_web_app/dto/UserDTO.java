package com.opticalarc.secure_web_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Username can not be blank")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password can not be blank")
    private String password;

    @NotBlank(message = "Email can not be blank")
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roles;
}
