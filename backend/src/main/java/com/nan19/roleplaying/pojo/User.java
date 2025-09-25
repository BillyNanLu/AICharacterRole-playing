package com.nan19.roleplaying.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users") // 数据库表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String userPic;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}