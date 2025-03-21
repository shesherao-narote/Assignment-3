package com.opticalarc.secure_web_app.controller;

import com.opticalarc.secure_web_app.dto.*;
import com.opticalarc.secure_web_app.exception.EmptyListFoundException;
import com.opticalarc.secure_web_app.exception.InvalidTokenException;
import com.opticalarc.secure_web_app.exception.InvalidUsernamePasswordException;
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
import java.util.Objects;

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
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginDTO loginDTO){
        String token = userService.verify(loginDTO);
        if (!Objects.equals(token, "failed")) {
            RefreshTokenDTO refreshToken = refreshTokenService.createRefreshToken(loginDTO.getUsername());

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            throw new InvalidUsernamePasswordException("Error", "Username & Password");
        }
    }



    @Operation(summary = "Refresh Token", description = "Generate a new access token using a refresh token.")
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshJwtToken(@RequestBody RefreshTokenRequest request) {

        RefreshTokenDTO refreshToken = null;
        try {
            refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        } catch (Exception e) {
            throw new InvalidTokenException("RefreshToken");
        }

            UserDTO user = refreshToken.getUser();
            String token = jwtUtil.generateToken(user.getUsername());
            jwtResponse.setJwtToken(token);
            jwtResponse.setRefreshToken(refreshToken.getRefreshToken());

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
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }catch (Exception e){
            throw new EmptyListFoundException("Users Not Found");
        }

    }


    @Operation(summary = "Get One User", description = "Fetch user by its id from the database.")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        try {
            UserDTO userDTO = userService.getUserById(userId);
            return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
        }catch (Exception e) {
            throw new EmptyListFoundException("User Not Found With"+userId);
        }
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

    public void testingMerge(){
        System.out.println("Testing Merge..!");
    }

    public void testingMerge3(){
        System.out.println("Testing Merge3..!");
    }

}
