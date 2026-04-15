package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Behavior;

import java.util.List;

public interface BehaviorService {
    // 添加行为记录
    void addBehavior(Long userId, String targetType, Long targetId, String action);

    // 切换行为状态（点赞/收藏）
    boolean toggleBehavior(Long userId, String targetType, Long targetId, String action);

    // 检查行为是否存在
    boolean isBehaviorExists(Long userId, String targetType, Long targetId, String action);

    // 获取用户行为列表
    List<Behavior> getUserBehaviorList(Long userId, String targetType, String action, int page, int size);

    // 统计用户行为数量
    Long countUserBehavior(Long userId, String targetType, String action);
}
