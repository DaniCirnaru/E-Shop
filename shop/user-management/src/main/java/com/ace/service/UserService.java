package com.ace.service;

import com.ace.dto.UserDTO;
import com.ace.entity.Role;
import com.ace.entity.User;
import com.ace.exception.EntityAlreadyExistsException;
import com.ace.exception.EntityNotFoundException;
import com.ace.mapper.UserMapper;
import com.ace.repository.RoleRepository;
import com.ace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
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
        return roleRepository.findById(userDTO.roleId()).orElseThrow(() -> new EntityNotFoundException("Entity with ID " + userDTO.roleId() + " not found"));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }
}
