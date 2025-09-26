package com.nan19.roleplaying.mapper;

import com.nan19.roleplaying.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM roles")
    List<Role> findAll();

    Role findById(Long id);
}
