package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Behavior;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BehaviorMapper {
    // 插入行为记录
    int insert(@Param("tableName") String tableName, @Param("behavior") Behavior behavior);

    // 删除行为记录
    int delete(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    // 查询行为记录
    Behavior select(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    // 查询行为记录列表
    List<Behavior> selectList(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("targetType") String targetType, @Param("offset") int offset, @Param("size") int size);

    // 统计行为记录数量
    Long countList(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("targetType") String targetType);
}
