package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.News;
import com.qqsystem.serve.mapper.NewsMapper;
import com.qqsystem.serve.service.NewsService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public News getById(Long id) {
        newsMapper.increaseViewCount(id);
        return newsMapper.selectById(id);
    }

    @Override
    public List<News> pageList(int page, int size) {
        int offset = (page - 1) * size;
        return newsMapper.selectList(offset, size);
    }

    @Override
    public Long countList() {
        return newsMapper.countList();
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
        newsMapper.increaseViewCount(id);
    }
}
