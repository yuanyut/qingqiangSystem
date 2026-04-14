package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.RecommendItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RecommendMapper {
    // 获取戏剧热门数据
    List<Map<String, Object>> getHotDramas();

    // 获取新闻热门数据
    List<Map<String, Object>> getHotNews();

    // 获取演员热门数据
    List<Map<String, Object>> getHotActors();

    // 获取用户行为数据
    List<Map<String, Object>> getUserBehaviors(@Param("userId") Long userId);

    // 获取所有用户行为数据（用于计算物品相似度）
    List<Map<String, Object>> getAllBehaviors();

    // 获取用户已浏览的物品
    List<Map<String, Object>> getUserViewedItems(@Param("userId") Long userId);

    // 获取热门内容列表（基于content_stats）
    List<Map<String, Object>> getHotContentList();

    // 获取用户最近浏览的内容
    List<Map<String, Object>> getUserRecentContent(@Param("userId") Long userId);

    // 获取内容的标签
    List<Map<String, Object>> getContentTags(@Param("contentId") Long contentId);

    // 获取拥有相同标签的内容
    List<Map<String, Object>> getContentByTagId(@Param("tagId") Long tagId, @Param("excludeContentId") Long excludeContentId);
}
