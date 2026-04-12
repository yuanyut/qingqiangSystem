package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.User;
import com.qqsystem.serve.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {

        // 1. 根据用户名查用户
        User user = userMapper.selectByUsername(username);

        // 2. 用户不存在
        if (user == null) {
            return null;
        }

        // 3. 校验密码（当前：明文）
        if (!user.getPassword().equals(password)) {
            return null;
        }

        // 4. 登录成功
        return user;
    }
}