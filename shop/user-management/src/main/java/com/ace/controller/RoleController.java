package com.ace.controller;

import com.ace.dto.RoleDTO;
import com.ace.entity.Role;
import com.ace.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public RoleDTO postRoleByID(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    @GetMapping("/{id}")
    public RoleDTO getRoleByID(@PathVariable("id") Long id) {
        return roleService.getRoleByID(id);
    }

}
