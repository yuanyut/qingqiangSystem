package com.qqsystem.serve.controller;

import com.qqsystem.serve.entity.User;
import com.qqsystem.serve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User result = userService.login(user.getUsername(), user.getPassword());

        if (result == null) {
            return "登录失败";
        }

        return "登录成功，欢迎：" + result.getNickname();
    }
}