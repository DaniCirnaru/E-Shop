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

        Role role = roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + userDTO.roleId() + " not found"));

        User user = userMapper.toUser(userDTO, role);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDTO(savedUser);
    }

}
