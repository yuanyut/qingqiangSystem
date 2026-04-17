package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.Content;
import com.qqsystem.serve.mapper.ContentMapper;
import org.springframework.stereotype.Service;  // 添加这个导入
import jakarta.annotation.Resource;  // 添加这个导入
import com.qqsystem.serve.service.ContentService;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentMapper contentMapper;

    @Override
    public List<Content> getCultureList(int page, int size, String category) {
        int offset = (page - 1) * size;
        return contentMapper.selectCultureList(offset, size, category);
    }

    @Override
    public Long countCultureList(String category) {
        return contentMapper.countCultureList(category);
    }

    @Override
    public Content getCultureDetail(Long id) {
        return contentMapper.selectCultureDetail(id);
    }

    @Override
    public boolean addCulture(Content content) {
        content.setBizType("culture");
        content.setViewCount(0);
        content.setLikeCount(0);
        return contentMapper.insert(content) > 0;
    }

    @Override
    public boolean updateCulture(Content content) {
        content.setBizType("culture");
        content.setType("article");
        return contentMapper.updateById(content) > 0;
    }

    @Override
    public boolean deleteCulture(Long id) {
        return contentMapper.deleteById(id) > 0;
    }

    @Override
    public List<Map<String, Object>> getCultureCategories() {
        return contentMapper.selectCultureCategories();
    }

    @Override
    public Content getTopCulture() {
        return contentMapper.selectTopCulture();
    }

    @Override
    public List<Content> getHotCulture(int size) {
        return contentMapper.selectHotCulture(size);
    }

    @Override
    public List<Content> getRecommendCulture(int size) {
        return contentMapper.selectRecommendCulture(size);
    }

    @Override
    public List<Content> getMediaCulture(int size) {
        return contentMapper.selectMediaCulture(size);
    }
}
