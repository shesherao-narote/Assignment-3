package com.opticalarc.secure_web_app.service;

import com.opticalarc.secure_web_app.dto.LoginDTO;
import com.opticalarc.secure_web_app.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO addUser(UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    String verify(LoginDTO loginDTO);
}
