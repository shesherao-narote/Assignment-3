package com.opticalarc.secure_web_app.serviceImpl;

import com.opticalarc.secure_web_app.dto.UserDTO;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.repository.UserRepository;
import com.opticalarc.secure_web_app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return null;
    }


}
