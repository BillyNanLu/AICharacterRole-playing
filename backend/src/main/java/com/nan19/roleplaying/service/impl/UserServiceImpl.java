package com.nan19.roleplaying.service.impl;

import com.nan19.roleplaying.mapper.UserMapper;
import com.nan19.roleplaying.pojo.User;
import com.nan19.roleplaying.service.UserService;
import com.nan19.roleplaying.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (userMapper.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("邮箱已存在");
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        User u = userMapper.findByUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            String token = JwtUtil.generateToken(u.getId(), u.getUsername());
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", u);
            return result;
        }
        throw new RuntimeException("用户名或密码错误");
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
}