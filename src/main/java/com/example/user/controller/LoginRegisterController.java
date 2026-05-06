package com.example.user.controller;

import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "用户名或密码错误");
        }
        model.addAttribute("title", "用户登录");
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password) {
        if (userService.authenticate(username, password)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {
        String error = userService.register(username, password, confirmPassword);
        if (error == null) {
            return "redirect:/login";
        } else {
            if (error.contains("用户名")) {
                model.addAttribute("usernameError", error);
            } else if (error.contains("密码长度")) {
                model.addAttribute("passwordError", error);
            } else if (error.contains("两次密码")) {
                model.addAttribute("confirmError", error);
            }
            model.addAttribute("username", username);
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}