package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper{
    User selectByUsername(@Param("username") String username);
    User selectById(@Param("userId") Long id );
    int insert(User user);
    int updateUserProfile(@Param("userId") Long userId, @Param("nickname") String nickname, @Param("info") String info, @Param("address") String address, @Param("sex") String sex, @Param("birthday") String birthday);
    int updateUserAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);
    int updatePassword(@Param("userId") Long userId, @Param("password") String password);
}
