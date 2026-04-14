package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.News;
import com.qqsystem.serve.mapper.NewsMapper;
import com.qqsystem.serve.service.NewsService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public News getById(Long id) {
        newsMapper.increaseView(id);
        return newsMapper.selectById(id);
    }

    @Override
    public List<News> pageList(int page, int size, Integer category, String keyword) {
        int offset = (page - 1) * size;
        return newsMapper.selectList(offset, size, category, keyword);
    }

    @Override
    public Long countList(Integer category, String keyword) {
        return newsMapper.countList(category, keyword);
    }

    @Override
    public boolean add(News news) {
        news.setViewCount(0);
        news.setLikeCount(0);
        news.setStatus(1);
        return newsMapper.insert(news) > 0;
    }

    @Override
    public boolean update(News news) {
        return newsMapper.updateById(news) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return newsMapper.deleteById(id) > 0;
    }

    @Override
    public void increaseView(Long id) {
        newsMapper.increaseView(id);
    }

    @Override
    public List<Map<String, Object>> getNewsCategories() {
        return newsMapper.selectNewsCategories();
    }

    @Override
    public News getTopNews() {
        return newsMapper.selectTopNews();
    }

    @Override
    public List<News> getHotNews(int size) {
        return newsMapper.selectHotNews(size);
    }

    @Override
    public List<News> getRecommendNews(int size) {
        return newsMapper.selectRecommendNews(size);
    }

    @Override
    public List<News> getMediaNews(int size) {
        return newsMapper.selectMediaNews(size);
    }
}
