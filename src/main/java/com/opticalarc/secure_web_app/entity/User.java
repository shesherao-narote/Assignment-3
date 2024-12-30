package com.opticalarc.secure_web_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 6, max = 50, message = "Username must be between 6 and 50 characters")
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email can not be blank ")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    @NotBlank(message = "Roles can not be blank")
    private String roles = "USER";

}
