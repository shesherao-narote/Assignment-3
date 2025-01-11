//package com.opticalarc.secure_web_app.controller;
//
//
//import com.opticalarc.secure_web_app.entity.User;
//import com.opticalarc.secure_web_app.exception.InvalidTokenException;
//import com.opticalarc.secure_web_app.exception.ResourceNotFoundException;
//import com.opticalarc.secure_web_app.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthenticationController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @GetMapping("/verify-email")
//    public String verifyEmailToken(@RequestParam("token") String token){
//        System.out.println(token);
//        User user = userRepository.findByEmailToken(token).orElseThrow(()->new ResourceNotFoundException("User", "Token",token));
//        if (user.getEmailToken().equals(token)){
//            user.setEmailVerified(true);
//            user.setEnabled(true);
//            userRepository.save(user);
//            return "Success";
//        }else {
//            throw new InvalidTokenException("Email Token is Invalid");
//        }
//    }
//}
