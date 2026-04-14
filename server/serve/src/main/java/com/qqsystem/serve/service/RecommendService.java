package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.RecommendItem;

import java.util.List;

public interface RecommendService {
    // 热门推荐
    List<RecommendItem> getHotRecommendations();

    // 协同过滤推荐
    List<RecommendItem> getCollaborativeFilteringRecommendations(Long userId);
}
