package com.ace.um.dto;

public record UserDTO(
        String username,
        String email,
        String password,
        Long roleId
) {
}
