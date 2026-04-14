package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.News;

import java.util.List;
import java.util.Map;

public interface NewsService {
    News getById(Long id);

    List<News> pageList(int page, int size);

    Long countList();

    boolean add(News news);

    boolean update(News news);

    boolean delete(Long id);

    void increaseView(Long id);

    // 获取新闻分类统计
    List<Map<String, Object>> getNewsCategories();

    // 获取头条资讯
    News getTopNews();

    // 获取热门排行
    List<News> getHotNews(int size);

    // 获取推荐阅读
    List<News> getRecommendNews(int size);

    // 获取媒体聚焦（视频类）
    List<News> getMediaNews(int size);
}
