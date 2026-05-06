package com.example.user.service;

import com.example.user.entity.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public String register(String username, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()) {
            return "用户名不能为空";
        }
        if (password == null || password.length() < 6) {
            return "密码长度不能少于6位";
        }
        if (!password.equals(confirmPassword)) {
            return "两次密码输入不一致";
        }
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return "用户名已被注册";
            }
        }
        userList.add(new User(username, password));
        return null;
    }

    public boolean authenticate(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}