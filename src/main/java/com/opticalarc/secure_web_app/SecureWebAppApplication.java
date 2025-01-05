package com.opticalarc.secure_web_app;

import com.opticalarc.secure_web_app.security.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureWebAppApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SecureWebAppApplication.class, args);

	}

}
