package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.Result;
import com.qqsystem.serve.common.utils.JwtUtil;
import com.qqsystem.serve.dto.UserDTO;
import com.qqsystem.serve.entity.User;
import com.qqsystem.serve.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {

        User result = userService.login(user.getUsername(), user.getPassword());

        if (result == null) {
            return Result.error("登录失败");
        }
        //生成这个用户的token
        String token= JwtUtil.generateToken(result.getId(),result.getUsername());
        System.out.println("token=" + token);
        return Result.success("登录成功，欢迎：" + result.getUsername(), token);
    }
    //获取用户信息
    @GetMapping("/me")
    public UserDTO me(HttpServletRequest request) {

        // 1. 获取 token
        String token = request.getHeader("Authorization");
        // 2. 解析token得到userId（关键）
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
        Long userId = JwtUtil.getUserId(token);

        // 3. 查数据库
        return userService.getUserInfo(userId);
    }

}