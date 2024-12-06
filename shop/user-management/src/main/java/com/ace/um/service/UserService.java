package com.ace.um.service;

import com.ace.um.dto.UserDTO;
import com.ace.um.entity.Role;
import com.ace.um.entity.User;
import com.ace.um.exception.EntityAlreadyExistsException;
import com.ace.um.exception.EntityNotFoundException;
import com.ace.um.mapper.UserMapper;
import com.ace.um.repository.RoleRepository;
import com.ace.um.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new EntityAlreadyExistsException("User with email" + userDTO.email() + "already exists");
        }

        Role role = findRoleById(userDTO);

        User user = userMapper.toUser(userDTO, role);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = findUserById(id);

        return userMapper.toUserDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = findUserById(id);
        Role role = findRoleById(userDTO);

        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(role);

        User updatedUser = userRepository.save(user);

        return userMapper.toUserDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    private Role findRoleById(UserDTO userDTO) {
        return roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + userDTO.roleId() + " not found"));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }
}