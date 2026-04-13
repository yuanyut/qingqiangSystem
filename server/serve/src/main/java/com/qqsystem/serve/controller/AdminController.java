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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result adminLogin(@RequestBody User user) {

        User result = userService.login(user.getUsername(), user.getPassword());

        if (result == null) {
            return Result.error("登录失败");
        }

        if (!"admin".equals(result.getRole())) {
            return Result.error("无权限访问管理系统");
        }

        String token = JwtUtil.generateToken(
                result.getId(),
                result.getUsername(),
                result.getRole()
        );
        System.out.println(token);
        return Result.success("管理员登录成功", token);
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
