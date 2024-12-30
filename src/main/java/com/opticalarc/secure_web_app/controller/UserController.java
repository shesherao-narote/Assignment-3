package com.opticalarc.secure_web_app.controller;

import com.opticalarc.secure_web_app.dto.UserDTO;
import com.opticalarc.secure_web_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        UserDTO savedUser = userService.addUser(userDTO);
        return new ResponseEntity<UserDTO>(savedUser,HttpStatus.CREATED);
    }

}