package com.ace.um.service;

import com.ace.um.dto.UserDTO;
import com.ace.um.entity.Role;
import com.ace.um.entity.User;
import com.ace.um.exception.EntityAlreadyExistsException;
import com.ace.um.exception.EntityNotFoundException;
import com.ace.um.mapper.UserMapper;
import com.ace.um.repository.RoleRepository;
import com.ace.um.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("TestUser", "test@example.com", "password", 1L);
        role = new Role();
        role.setId(1L);
        role.setRoleName("USER");

        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(role);
    }

    @Test
    void createUser_Success() {
        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.empty());
        when(roleRepository.findById(userDTO.roleId())).thenReturn(Optional.of(role));
        when(userMapper.toUser(userDTO, role)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(userDTO, result);
        verify(userRepository).findByEmail(userDTO.email());
        verify(roleRepository).findById(userDTO.roleId());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_EmailAlreadyExists() {

        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.of(user));

        assertThrows(EntityAlreadyExistsException.class, () -> userService.createUser(userDTO));
        verify(userRepository).findByEmail(userDTO.email());
        verifyNoInteractions(roleRepository, userMapper);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository).findById(1L);
    }


    @Test
    void updateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(userDTO.roleId())).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.updateUser(1L, userDTO);

        assertEquals(userDTO, result);
        verify(userRepository).findById(1L);
        verify(roleRepository).findById(userDTO.roleId());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1L, userDTO));
        verify(userRepository).findById(1L);
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository).findById(1L);
    }
}
