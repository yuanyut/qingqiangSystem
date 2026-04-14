package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Behavior;

import java.util.List;

public interface BehaviorService {
    // 浏览记录
    void recordBrowse(Long userId, String targetType, Long targetId);

    // 点赞/取消点赞
    boolean toggleLike(Long userId, String targetType, Long targetId);

    // 收藏/取消收藏
    boolean toggleFavorite(Long userId, String targetType, Long targetId);

    // 检查是否已点赞
    boolean isLiked(Long userId, String targetType, Long targetId);

    // 检查是否已收藏
    boolean isFavorited(Long userId, String targetType, Long targetId);

    // 获取用户点赞列表
    List<Behavior> getLikeList(Long userId, String targetType, int page, int size);

    // 获取用户收藏列表
    List<Behavior> getFavoriteList(Long userId, String targetType, int page, int size);

    // 获取用户浏览历史
    List<Behavior> getBrowseHistory(Long userId, String targetType, int page, int size);
}
