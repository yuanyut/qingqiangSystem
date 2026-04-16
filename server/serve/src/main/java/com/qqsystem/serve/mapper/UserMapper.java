package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper{
    User selectByUsername(@Param("username") String username);
    User selectById(@Param("userId") Long id );
    int insert(User user);
    int updateUserProfile(@Param("userId") Long userId, @Param("nickname") String nickname, @Param("info") String info, @Param("address") String address, @Param("sex") String sex, @Param("birthday") String birthday);
    int updateUserAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);
    int updatePassword(@Param("userId") Long userId, @Param("password") String password);
    List<Map<String, Object>> selectUserProvinceStats();
    List<Map<String, Object>> selectUserAgeDistribution();
    List<Map<String, Object>> selectUserGenderDistribution();
    List<User> selectUserList(@Param("params") Map<String, Object> params);
    int selectUserCount(@Param("params") Map<String, Object> params);
    int deleteUserById(@Param("id") Long id);
    int deleteUserByIds(@Param("ids") List<Long> ids);
    int updateUserStatus(@Param("id") Long id, @Param("status") Integer status);
    int updateUser(User user);
}
