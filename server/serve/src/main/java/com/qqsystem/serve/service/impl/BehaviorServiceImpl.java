package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.Behavior;
import com.qqsystem.serve.mapper.ActorMapper;
import com.qqsystem.serve.mapper.BehaviorMapper;
import com.qqsystem.serve.mapper.ContentMapper;
import com.qqsystem.serve.mapper.DramaMapper;
import com.qqsystem.serve.mapper.NewsMapper;
import com.qqsystem.serve.service.BehaviorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BehaviorServiceImpl implements BehaviorService {

    @Resource
    private BehaviorMapper behaviorMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private DramaMapper dramaMapper;

    @Resource
    private ActorMapper actorMapper;

    @Resource
    private NewsMapper newsMapper;

    @Override
    @Transactional
    public void addBehavior(Long userId, String targetType, Long targetId, String action) {
        Behavior existing = behaviorMapper.selectBehavior(userId, targetType, targetId, action);
        if (existing != null) {
            // If exists, update status to 1
            behaviorMapper.updateBehaviorStatus(userId, targetType, targetId, action, 1);
        } else {
            // If not exists, insert new record
            Behavior behavior = new Behavior();
            behavior.setUserId(userId);
            behavior.setTargetType(targetType);
            behavior.setTargetId(targetId);
            behavior.setAction(action);
            behaviorMapper.insertBehavior(behavior);
        }

        // Update view count based on biz_type
        if ("view".equals(action)) {
            updateViewCount(targetType, targetId);
        }
    }

    @Override
    @Transactional
    public boolean toggleBehavior(Long userId, String targetType, Long targetId, String action) {
        Behavior existing = behaviorMapper.selectBehavior(userId, targetType, targetId, action);
        if (existing != null) {
            // Toggle status: if 1 -> 0, if 0 -> 1
            int newStatus = existing.getStatus() == 1 ? 0 : 1;
            behaviorMapper.updateBehaviorStatus(userId, targetType, targetId, action, newStatus);

            // Update like count based on status change
            if ("like".equals(action)) {
                if (newStatus == 1) {
                    // Status 0 -> 1: increase like count
                    increaseLikeCount(targetType, targetId);
                } else {
                    // Status 1 -> 0: decrease like count
                    decreaseLikeCount(targetType, targetId);
                }
            }

            return newStatus == 1;
        } else {
            // If not exists, insert with status 1
            addBehavior(userId, targetType, targetId, action);

            // Increase like count for first time like
            if ("like".equals(action)) {
                increaseLikeCount(targetType, targetId);
            }

            return true;
        }
    }

    @Override
    public boolean isBehaviorExists(Long userId, String targetType, Long targetId, String action) {
        Behavior existing = behaviorMapper.selectBehavior(userId, targetType, targetId, action);
        return existing != null && existing.getStatus() == 1;
    }

    @Override
    public List<Behavior> getUserBehaviorList(Long userId, String targetType, String action, int page, int size) {
        int offset = (page - 1) * size;
        return behaviorMapper.selectUserBehaviorList(userId, targetType, action, offset, size);
    }

    @Override
    public Long countUserBehavior(Long userId, String targetType, String action) {
        return behaviorMapper.countUserBehavior(userId, targetType, action);
    }

    /**
     * Update view count based on biz_type
     * Only drama and culture update content table
     * actor and news only update their own tables
     */
    private void updateViewCount(String targetType, Long targetId) {
        switch (targetType) {
            case "drama":
                // drama: content.view_count +1, drama.view_count +1
                contentMapper.increaseViewCount(targetId);
                dramaMapper.increaseViewCount(targetId);
                break;
            case "culture":
                // culture: content.view_count +1
                contentMapper.increaseViewCount(targetId);
                break;
            case "actor":
                // actor: actor.view_count +1
                actorMapper.increaseViewCount(targetId);
                break;
            case "news":
                // news: news.view_count +1
                newsMapper.increaseView(targetId);
                break;
            default:
                // Do nothing for unknown types
                break;
        }
    }

    /**
     * Increase like count based on biz_type
     * Only drama and culture update content table
     * actor and news only update their own tables
     */
    private void increaseLikeCount(String targetType, Long targetId) {
        switch (targetType) {
            case "drama":
                // drama: content.like_count +1, drama.like_count +1
                contentMapper.increaseLikeCount(targetId);
                dramaMapper.increaseLikeCount(targetId);
                break;
            case "culture":
                // culture: content.like_count +1
                contentMapper.increaseLikeCount(targetId);
                break;
            case "actor":
                // actor: actor.like_count +1
                actorMapper.increaseLikeCount(targetId);
                break;
            case "news":
                // news: news.like_count +1
                newsMapper.increaseLikeCount(targetId);
                break;
            default:
                // Do nothing for unknown types
                break;
        }
    }

    /**
     * Decrease like count based on biz_type
     * Only drama and culture update content table
     * actor and news only update their own tables
     */
    private void decreaseLikeCount(String targetType, Long targetId) {
        switch (targetType) {
            case "drama":
                // drama: content.like_count -1, drama.like_count -1
                contentMapper.decreaseLikeCount(targetId);
                dramaMapper.decreaseLikeCount(targetId);
                break;
            case "culture":
                // culture: content.like_count -1
                contentMapper.decreaseLikeCount(targetId);
                break;
            case "actor":
                // actor: actor.like_count -1
                actorMapper.decreaseLikeCount(targetId);
                break;
            case "news":
                // news: news.like_count -1
                newsMapper.decreaseLikeCount(targetId);
                break;
            default:
                // Do nothing for unknown types
                break;
        }
    }
}
