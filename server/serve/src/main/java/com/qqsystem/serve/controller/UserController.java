package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.common.utils.JwtUtil;
import com.qqsystem.serve.dto.RegisterDTO;
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
    public ResponseResult<?> login(@RequestBody User user) {

        User result = userService.login(user.getUsername(), user.getPassword());

        if (result == null) {
            return ResponseResult.badRequest("登录失败");
        }
        //生成这个用户的token
        String token = JwtUtil.generateToken(
                result.getId(),
                result.getUsername(),
                result.getRole()
        );
        System.out.println("token=" + token);
        return ResponseResult.success(token);
    }
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

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
        return ResponseResult.success("注册成功");
    }
}