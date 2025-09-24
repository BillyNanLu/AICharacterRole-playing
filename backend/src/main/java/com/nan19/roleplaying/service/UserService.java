package com.nan19.roleplaying.service;

import com.nan19.roleplaying.pojo.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
}
