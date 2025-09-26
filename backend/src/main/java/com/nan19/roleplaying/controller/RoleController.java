package com.nan19.roleplaying.controller;

import com.nan19.roleplaying.pojo.Role;
import com.nan19.roleplaying.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }
}