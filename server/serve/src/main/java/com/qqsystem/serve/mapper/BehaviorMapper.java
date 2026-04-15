package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Behavior;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BehaviorMapper {
    // 插入行为记录到统一表
    int insertBehavior(@Param("behavior") Behavior behavior);

    // 更新行为状态
    int updateBehaviorStatus(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId, @Param("action") String action, @Param("status") Integer status);

    // 查询行为记录
    Behavior selectBehavior(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId, @Param("action") String action);

    // 查询用户行为列表
    List<Behavior> selectUserBehaviorList(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("action") String action, @Param("offset") int offset, @Param("size") int size);

    // 统计用户行为数量
    Long countUserBehavior(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("action") String action);
    
    // 根据id更新行为状态
    int updateBehaviorStatusById(@Param("id") Long id, @Param("status") Integer status);
    
    // 批量更新用户行为状态
    int batchUpdateBehaviorStatus(@Param("userId") Long userId, @Param("action") String action, @Param("status") Integer status);
}
