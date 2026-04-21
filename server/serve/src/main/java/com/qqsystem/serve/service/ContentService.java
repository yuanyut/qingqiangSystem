package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Content;

import java.util.List;
import java.util.Map;

public interface ContentService {
    List<Content> getCultureList(int page, int size, String category, String keyword, Integer status);

    Long countCultureList(String category, String keyword, Integer status);

    Content getCultureDetail(Long id);

    boolean addCulture(Content content);

    boolean updateCulture(Content content);

    boolean deleteCulture(Long id);

    // 获取文化资讯分类统计
    List<Map<String, Object>> getCultureCategories();

    // 获取头条资讯
    Content getTopCulture();

    // 获取热门排行
    List<Content> getHotCulture(int size);

    // 获取推荐阅读
    List<Content> getRecommendCulture(int size);

    // 获取媒体聚焦（视频类）
    List<Content> getMediaCulture(int size);
}
