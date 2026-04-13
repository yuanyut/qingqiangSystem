package com.qqsystem.serve.service;

import com.qqsystem.serve.dto.RegisterDTO;
import com.qqsystem.serve.dto.UserDTO;
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
    //    查登录用户的明细
    public UserDTO getUserInfo(Long userId) {

        User user = userMapper.selectById(userId);

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setAvatar(user.getAvatar());
        System.out.println(dto);
        return dto;
    }
    //注册
    public void register(RegisterDTO dto) {

        // 1. 用户名是否存在
        User exist = userMapper.selectByUsername(dto.getUsername());
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }



        // 3. 封装用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setSex(dto.getSex());
        user.setBirthday(dto.getBirthday());
        user.setStatus(1);

        // 4. 入库
        userMapper.insert(user);
    }
}