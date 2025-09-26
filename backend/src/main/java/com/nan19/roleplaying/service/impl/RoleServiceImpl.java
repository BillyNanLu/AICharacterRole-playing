package com.nan19.roleplaying.service.impl;

import com.nan19.roleplaying.mapper.RoleMapper;
import com.nan19.roleplaying.pojo.Role;
import com.nan19.roleplaying.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.findById(id);
    }
}