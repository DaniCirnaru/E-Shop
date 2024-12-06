package com.ace.um.mapper;

import com.ace.um.dto.UserDTO;
import com.ace.um.entity.Role;
import com.ace.um.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId()
        );
    }

    public User toUser(UserDTO userDTO, Role role) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(role);
        return user;
    }
}
