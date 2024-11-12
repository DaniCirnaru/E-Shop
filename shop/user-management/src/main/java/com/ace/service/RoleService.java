package com.ace.service;

import com.ace.dto.RoleDTO;
import com.ace.entity.Role;
import com.ace.exception.EntityNotFoundException;
import com.ace.mapper.RoleMapper;
import com.ace.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleMapper.toRole(roleDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toRoleDTO(savedRole);
    }

    public RoleDTO getRoleByID(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role with ID " + id + " not found"));
        return roleMapper.toRoleDTO(role);
    }
}
