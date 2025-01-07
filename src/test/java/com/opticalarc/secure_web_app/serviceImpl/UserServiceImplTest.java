package com.opticalarc.secure_web_app.serviceImpl;

import com.opticalarc.secure_web_app.dto.UserDTO;
import com.opticalarc.secure_web_app.entity.User;
import com.opticalarc.secure_web_app.exception.ResourceNotFoundException;
import com.opticalarc.secure_web_app.repository.UserRepository;
import com.opticalarc.secure_web_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;



    private UserDTO userDTO;
    private User existedUser;
    private User updatedUser;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setEmail("newemail@example.com");
        userDTO.setUsername("newusername");
        userDTO.setPassword("newpassword");

        existedUser = new User();
        existedUser.setId(1L);
        existedUser.setEmail("oldemail@example.com");
        existedUser.setUsername("oldusername");
        existedUser.setPassword("oldpassword");

        updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setUsername("newusername");
        updatedUser.setPassword("newpassword");
    }

    @Test
    void testUpdateUser() {
        // Setup mocks
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(existedUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        // Execute the method to test
        UserDTO result = userService.updateUser(1L, userDTO);

        // Verify the interactions and assert the result
        verify(userRepository).findById(1L);
        verify(userRepository).save(existedUser);
        verify(modelMapper).map(updatedUser, UserDTO.class);

        assertNotNull(result);
        assertEquals("newemail@example.com", result.getEmail());
        assertEquals("newusername", result.getUsername());
        assertEquals("newpassword", result.getPassword());
    }



    @Test
    void testGetAllUsers() {

        // Mock data
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("JohnDoe");
        user1.setEmail("johndoe@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("JaneDoe");
        user2.setEmail("janedoe@example.com");

        List<User> users = Arrays.asList(user1, user2);

        // Mock repository behavior
        when(userRepository.findAll()).thenReturn(users);

        // Mock model mapper behavior
        UserDTO userDTO1 = new UserDTO(1L, "JohnDoe", "johndoe@example.com","johndoe@example.com","ROLE_USER");
        UserDTO userDTO2 = new UserDTO(2L, "JaneDoe", "janedoe@example.com","janedoe@example.com","ROLE_ADMIN");

        when(modelMapper.map(user1, UserDTO.class)).thenReturn(userDTO1);
        when(modelMapper.map(user2, UserDTO.class)).thenReturn(userDTO2);

        // Call the method under test
        List<UserDTO> result = userService.getAllUsers();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("JohnDoe", result.get(0).getUsername());
        assertEquals("johndoe@example.com", result.get(0).getEmail());

        assertEquals("JaneDoe", result.get(1).getUsername());
        assertEquals("janedoe@example.com", result.get(1).getEmail());

        // Verify interactions
        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user1, UserDTO.class);
        verify(modelMapper, times(1)).map(user2, UserDTO.class);
    }



    @Test
    void testGetUserById_Success() {
        // Mock data
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("JohnDoe");
        user.setEmail("johndoe@example.com");

        UserDTO userDTO = new UserDTO(userId, "JohnDoe", "john123","johndoe@example.com","ROLE_USER");

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock model mapper behavior
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Call the method under test
        UserDTO result = userService.getUserById(userId);

        // Assertions
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("JohnDoe", result.getUsername());
        assertEquals("johndoe@example.com", result.getEmail());

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    void testGetUserById_NotFound() {
        // Mock data
        Long userId = 1L;

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the method and expect exception
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserById(userId)
        );

        // Assertions
        assertEquals("User not found with Id : " + userId, exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(modelMapper); // Ensure model mapper is not called
    }


    @Test
    void testAddUser() {
        // Mock data
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("JohnDoe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setPassword("plaintextpassword");
        userDTO.setRoles("ROLE_USER");

        User user = new User();
        user.setUsername("JohnDoe");
        user.setEmail("johndoe@example.com");
        user.setPassword("encodedpassword"); // Simulated encoded password
        user.setRoles("ROLE_USER");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("JohnDoe");
        savedUser.setEmail("johndoe@example.com");
        savedUser.setPassword("encodedpassword");
        savedUser.setRoles("ROLE_USER");

        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setId(1L);
        savedUserDTO.setUsername("JohnDoe");
        savedUserDTO.setEmail("johndoe@example.com");

        // Mock behavior
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedpassword");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserDTO.class)).thenReturn(savedUserDTO);

        // Call the method under test
        UserDTO result = userService.addUser(userDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("JohnDoe", result.getUsername());
        assertEquals("johndoe@example.com", result.getEmail());

        // Verify interactions
        verify(modelMapper, times(1)).map(userDTO, User.class);
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(modelMapper, times(1)).map(savedUser, UserDTO.class);
    }



    @Test
    void testDeleteUser_Success() {
        // Mock data
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("JohnDoe");

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Call the method under test
        userService.deleteUser(userId);

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Mock data
        Long userId = 1L;

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the method and expect exception
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.deleteUser(userId)
        );

        // Assertions
        assertEquals("User not found with Id : " + userId, exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class)); // Ensure delete is never called
    }



    @Test
    void testUpdateUser_Success() {
        // Mock data
        Long userId = 1L;

        User existedUser = new User();
        existedUser.setId(userId);
        existedUser.setEmail("oldemail@example.com");
        existedUser.setUsername("OldUsername");
        existedUser.setPassword("oldpassword");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newemail@example.com");
        userDTO.setUsername("NewUsername");
        userDTO.setPassword("newpassword");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setUsername("NewUsername");
        updatedUser.setPassword("newpassword");

        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(userId);
        updatedUserDTO.setEmail("newemail@example.com");
        updatedUserDTO.setUsername("NewUsername");

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(userRepository.save(existedUser)).thenReturn(updatedUser);

        // Mock model mapper behavior
        when(modelMapper.map(updatedUser, UserDTO.class)).thenReturn(updatedUserDTO);

        // Call the method under test
        UserDTO result = userService.updateUser(userId, userDTO);

        // Assertions
        assertNotNull(result);
        assertEquals("newemail@example.com", result.getEmail());
        assertEquals("NewUsername", result.getUsername());

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existedUser);
        verify(modelMapper, times(1)).map(updatedUser, UserDTO.class);
    }

    @Test
    void testUpdateUser_NotFound() {
        // Mock data
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the method and expect exception
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(userId, userDTO)
        );

        // Assertions
        assertEquals("User not found with Id : " + userId, exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class)); // Ensure save is not called
        verifyNoInteractions(modelMapper); // Ensure model mapper is not called
    }
}