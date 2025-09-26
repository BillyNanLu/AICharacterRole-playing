package com.nan19.roleplaying.service;

import com.nan19.roleplaying.pojo.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}