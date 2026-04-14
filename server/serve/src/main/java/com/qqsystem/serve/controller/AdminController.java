package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
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
    public ResponseResult<?> adminLogin(@RequestBody User user) {

        User result = userService.login(user.getUsername(), user.getPassword());

        if (result == null) {
            return ResponseResult.badRequest("登录失败");
        }

        if (!"admin".equals(result.getRole())) {
            return ResponseResult.badRequest("无权限访问管理系统");
        }

        String token = JwtUtil.generateToken(
                result.getId(),
                result.getUsername(),
                result.getRole()
        );
        System.out.println(token);
        return ResponseResult.success(token);
    }
    //获取用户信息
    @GetMapping("/me")
    public ResponseResult<UserDTO> me(HttpServletRequest request) {

        // 1. 获取 token
        String token = request.getHeader("Authorization");
        // 2. 解析token得到userId（关键）
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
        Long userId = JwtUtil.getUserId(token);

        // 3. 查数据库
        UserDTO userDTO = userService.getUserInfo(userId);
        if (userDTO != null) {
            return ResponseResult.success(userDTO);
        } else {
            return ResponseResult.badRequest("用户不存在");
        }
    }
}
