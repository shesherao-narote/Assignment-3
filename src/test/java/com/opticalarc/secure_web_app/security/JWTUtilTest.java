//package com.opticalarc.secure_web_app.security;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.security.Key;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class JWTUtilTest {
//
//    @InjectMocks
//    private JWTUtil jwtUtil;
//
//    @Mock
//    private Key key;
//
//    @BeforeEach
//    void setUp() {
//        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    }
//
//    @Test
//    void testGenerateToken() {
//        String username = "testuser";
//
//        String token = jwtUtil.generateToken(username);
//
//        assertNotNull(token);
//    }
//}