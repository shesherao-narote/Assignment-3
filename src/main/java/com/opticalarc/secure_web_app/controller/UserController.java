package com.opticalarc.secure_web_app.controller;

import com.opticalarc.secure_web_app.dto.*;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.security.JWTUtil;
import com.opticalarc.secure_web_app.service.RefreshTokenService;
import com.opticalarc.secure_web_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private JwtResponse jwtResponse;

    @Operation(summary = "Login", description = "Authenticate a user and generate an access token.")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody UserDTO userDTO){
        String token = userService.verify(userDTO);
        RefreshTokenDTO refreshToken = refreshTokenService.createRefreshToken(userDTO.getUsername());

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Refresh Token", description = "Generate a new access token using a refresh token.")
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshJwtToken(@RequestBody RefreshTokenRequest request) {

        try {
            RefreshTokenDTO refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
            UserDTO user = refreshToken.getUser();
            String token = jwtUtil.generateToken(user.getUsername());
            jwtResponse.setJwtToken(token);
            jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }

    @Operation(summary = "Register", description = "Add user into the data base with the default role(ROLE_USER).")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        UserDTO savedUser = userService.addUser(userDTO);
        return new ResponseEntity<UserDTO>(savedUser,HttpStatus.CREATED);
    }


    @Operation(summary = "Get All Users", description = "Fetch all users from the database.")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @Operation(summary = "Get One User", description = "Fetch user by its id from the database.")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserById(userId);
        return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
    }


    @Operation(summary = "Update User", description = "Update a user by providing its id.")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }


    @Operation(summary = "Delete User", description = "Fetch all users from the database.")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User with id " + userId + " deleted successfully",true),HttpStatus.OK);
    }


    @Operation(summary = "Admin endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> getAdminPage(){
        return ResponseEntity.ok("Welcome to ADMIN page!!!");
    }


    @Operation(summary = "User endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/normalUser")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> getUserPage(){
        return ResponseEntity.ok("Welcome to USER page!!!");
    }

}