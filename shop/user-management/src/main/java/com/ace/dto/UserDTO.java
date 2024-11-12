package com.ace.dto;

public record UserDTO(
        String username,
        String email,
        String password,
        Long roleId
) {
}
