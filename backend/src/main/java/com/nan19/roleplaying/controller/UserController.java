package com.nan19.roleplaying.controller;

import com.nan19.roleplaying.pojo.User;
import com.nan19.roleplaying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> res = new HashMap<>();
        try {
            User saved = userService.register(user);
            res.put("success", true);
            res.put("user", saved);
        } catch (RuntimeException e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        User u = userService.login(loginData.get("username"), loginData.get("password"));
        Map<String, Object> res = new HashMap<>();
        if (u != null) {
            res.put("success", true);
            res.put("user", u);
        } else {
            res.put("success", false);
            res.put("message", "用户名或密码错误");
        }
        return res;
    }
}