package com.opticalarc.secure_web_app.exception;

import com.opticalarc.secure_web_app.dto.ApiResponse;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse> invalidTokenExceptionHandler(InvalidTokenException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUsernamePasswordException.class)
    public ResponseEntity<ApiResponse> InvalidUsernamePasswordException(InvalidTokenException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyListFoundException.class)
    public ResponseEntity<ApiResponse> emptyListFoundExceptionHandler(EmptyListFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameExtractException.class)
    public ResponseEntity<ApiResponse> usernameExtractExceptionHandler(UsernameExtractException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse> signatureExceptionHandler(SignatureException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse> malformedJwtExceptionHandler(MalformedJwtException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }


    // Handle unauthorized access
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "You are not authorized to access this resource");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // Handle unauthorized access (AuthenticationCredentialsNotFoundException)
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationCredentialsNotFound(
            AuthenticationCredentialsNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Unauthorized access");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Handle BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>("Invalid Credentials: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // Handle generic authentication exceptions (AuthenticationException)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Authentication error");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalExceptions(Exception ex) {
        ApiResponse apiResponse = new ApiResponse("An unexpected error occurred: " + ex.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
