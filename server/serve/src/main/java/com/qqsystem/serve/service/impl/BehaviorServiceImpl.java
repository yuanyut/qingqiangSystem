package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.Behavior;
import com.qqsystem.serve.mapper.BehaviorMapper;
import com.qqsystem.serve.service.BehaviorService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BehaviorServiceImpl implements BehaviorService {

    @Resource
    private BehaviorMapper behaviorMapper;

    private static final String TABLE_LIKE = "`like`";
    private static final String TABLE_FAVORITE = "favorite";
    private static final String TABLE_BROWSE_HISTORY = "browse_history";

    @Override
    public void recordBrowse(Long userId, String targetType, Long targetId) {
        // 检查是否已存在浏览记录
        Behavior existing = behaviorMapper.select(TABLE_BROWSE_HISTORY, userId, targetType, targetId);
        if (existing == null) {
            // 不存在则插入新记录
            Behavior behavior = new Behavior();
            behavior.setUserId(userId);
            behavior.setTargetType(targetType);
            behavior.setTargetId(targetId);
            behaviorMapper.insert(TABLE_BROWSE_HISTORY, behavior);
        }
    }

    @Override
    public boolean toggleLike(Long userId, String targetType, Long targetId) {
        return toggleBehavior(TABLE_LIKE, userId, targetType, targetId);
    }

    @Override
    public boolean toggleFavorite(Long userId, String targetType, Long targetId) {
        return toggleBehavior(TABLE_FAVORITE, userId, targetType, targetId);
    }

    @Override
    public boolean isLiked(Long userId, String targetType, Long targetId) {
        return isBehaviorExists(TABLE_LIKE, userId, targetType, targetId);
    }

    @Override
    public boolean isFavorited(Long userId, String targetType, Long targetId) {
        return isBehaviorExists(TABLE_FAVORITE, userId, targetType, targetId);
    }

    @Override
    public List<Behavior> getLikeList(Long userId, String targetType, int page, int size) {
        int offset = (page - 1) * size;
        return behaviorMapper.selectList(TABLE_LIKE, userId, targetType, offset, size);
    }

    @Override
    public List<Behavior> getFavoriteList(Long userId, String targetType, int page, int size) {
        int offset = (page - 1) * size;
        return behaviorMapper.selectList(TABLE_FAVORITE, userId, targetType, offset, size);
    }

    @Override
    public List<Behavior> getBrowseHistory(Long userId, String targetType, int page, int size) {
        int offset = (page - 1) * size;
        return behaviorMapper.selectList(TABLE_BROWSE_HISTORY, userId, targetType, offset, size);
    }

    // 通用的行为切换逻辑
    private boolean toggleBehavior(String tableName, Long userId, String targetType, Long targetId) {
        Behavior existing = behaviorMapper.select(tableName, userId, targetType, targetId);
        if (existing != null) {
            // 已存在则删除（取消）
            behaviorMapper.delete(tableName, userId, targetType, targetId);
            return false;
        } else {
            // 不存在则插入（添加）
            Behavior behavior = new Behavior();
            behavior.setUserId(userId);
            behavior.setTargetType(targetType);
            behavior.setTargetId(targetId);
            behaviorMapper.insert(tableName, behavior);
            return true;
        }
    }

    // 检查行为是否存在
    private boolean isBehaviorExists(String tableName, Long userId, String targetType, Long targetId) {
        Behavior existing = behaviorMapper.select(tableName, userId, targetType, targetId);
        return existing != null;
    }
}
