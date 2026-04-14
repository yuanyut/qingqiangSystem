package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.Content;
import com.qqsystem.serve.mapper.ContentMapper;
import org.springframework.stereotype.Service;  // 添加这个导入
import jakarta.annotation.Resource;  // 添加这个导入
import com.qqsystem.serve.service.ContentService;
import java.util.List;

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
        contentMapper.increaseViewCount(id);
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
        return contentMapper.updateById(content) > 0;
    }

    @Override
    public boolean deleteCulture(Long id) {
        return contentMapper.deleteById(id) > 0;
    }
}
