package com.nan19.roleplaying.controller;

import com.nan19.roleplaying.pojo.User;
import com.nan19.roleplaying.service.UserService;
import com.nan19.roleplaying.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        Map<String, Object> res = userService.login(data.get("username"), data.get("password"));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = JwtUtil.getUserId(token);
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}