package com.opticalarc.secure_web_app.serviceImpl;

import com.opticalarc.secure_web_app.dto.UserDTO;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.exception.ResourceNotFoundException;
import com.opticalarc.secure_web_app.repository.UserRepository;
import com.opticalarc.secure_web_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user ->
                modelMapper.map(user,UserDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "Id", id));
        return modelMapper.map(user, UserDTO.class);
    }


    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles("ROLE_USER");
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "Id", id));
        userRepository.delete(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existedUser = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "Id", id));
        existedUser.setEmail(userDTO.getEmail());
        existedUser.setUsername(userDTO.getUsername());
        existedUser.setPassword(userDTO.getPassword());
        userRepository.save(existedUser);
        return modelMapper.map(existedUser, UserDTO.class);
    }


}
