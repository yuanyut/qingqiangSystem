package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.RecommendItem;

import java.util.List;
import java.util.Map;

public interface RecommendService {
    // 热门推荐
    List<RecommendItem> getHotRecommendations();

    // 协同过滤推荐
    List<RecommendItem> getCollaborativeFilteringRecommendations(Long userId);

    // 首页推荐
    Map<String, List<RecommendItem>> getHomeRecommendations(Long userId);

    // 获取热门内容列表
    List<RecommendItem> getHotList();

    // 获取基于标签的推荐
    List<RecommendItem> getTagRecommend(Long userId);
}
