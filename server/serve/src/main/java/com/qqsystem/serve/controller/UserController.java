package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.common.utils.JwtUtil;
import com.qqsystem.serve.dto.RegisterDTO;
import com.qqsystem.serve.dto.UserDTO;
import com.qqsystem.serve.entity.User;
import com.qqsystem.serve.service.UserService;

import io.jsonwebtoken.Claims;
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
    
    /**
     * 获取当前登录用户信息
     * 必须携带token
     * 后端通过token解析userId
     * 查询数据库返回用户信息
     */
    @GetMapping("/me")
    public ResponseResult<UserDTO> me(HttpServletRequest request) {
        try {
            // 1. 获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return ResponseResult.badRequest("未登录");
            }

            // 2. 去掉 Bearer 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 3. 解析token得到userId
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);

            // 4. 查数据库
            UserDTO userDTO = userService.getUserInfo(userId);
            if (userDTO != null) {
                return ResponseResult.success(userDTO);
            } else {
                return ResponseResult.badRequest("用户不存在");
            }
        } catch (Exception e) {
            System.out.println("token解析失败: " + e.getMessage());
            return ResponseResult.badRequest("Token无效或已过期");
        }
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
        return ResponseResult.success("注册成功");
    }
}
