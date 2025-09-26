package com.nan19.roleplaying.service;

import com.nan19.roleplaying.pojo.User;

import java.util.Map;

public interface UserService {
    /**
     * 注册用户
     */
    User register(User user);

    /**
     * 登录并返回 token + user
     */
    Map<String, Object> login(String username, String password);

    /**
     * 根据 id 查找用户（用于 /me）
     */
    User findById(Long id);
}