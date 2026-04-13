package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.*;
import com.qqsystem.serve.mapper.*;
import com.qqsystem.serve.service.DramaService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DramaServiceImpl implements DramaService {

    @Resource
    private DramaMapper dramaMapper;

    @Resource
    private DramaActorMapper dramaActorMapper;

    @Resource
    private DramaContentMapper dramaContentMapper;

    @Resource
    private ContentMapper contentMapper;

    @Override
    public Drama getById(Long id) {
        dramaMapper.increaseViewCount(id);
        return dramaMapper.selectById(id);
    }

    // ⭐ 核心增强方法
    public Drama getDetailWithRelation(Long id) {

        Drama drama = dramaMapper.selectById(id);

        if (drama == null) {
            return null;
        }

        // actors - 处理空表情况
        List<DramaActor> actors = dramaActorMapper.selectByDramaId(id);
        if (actors == null || actors.isEmpty()) {
            drama.setActors(new ArrayList<>());
        } else {
            List<DramaActor> validActors = new ArrayList<>();
            for (DramaActor actor : actors) {
                if (actor != null) {
                    validActors.add(actor);
                }
            }
            drama.setActors(validActors);
        }

        // contents
        List<DramaContent> relations = dramaContentMapper.selectByDramaId(id);
        List<Content> contents = new ArrayList<>();
        for (DramaContent dc : relations) {
            if (dc != null) {
                Content c = contentMapper.selectById(dc.getContentId());
                if (c != null) {
                    contents.add(c);
                }
            }
        }
        drama.setContents(contents);

        return drama;
    }
    @Override
    public List<Drama> pageList(int page, int size, Long categoryId) {
        int offset = (page - 1) * size;
        return dramaMapper.selectList(offset, size, categoryId);
    }

    @Override
    public Long countList(Long categoryId) {
        return dramaMapper.countList(categoryId);
    }

    @Override
    public boolean add(Drama drama) {
        drama.setViewCount(0);
        drama.setStatus(1);
        return dramaMapper.insert(drama) > 0;
    }

    @Override
    public boolean update(Drama drama) {
        return dramaMapper.updateById(drama) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return dramaMapper.deleteById(id) > 0;
    }

    @Override
    public void increaseView(Long id) {
        dramaMapper.increaseViewCount(id);
    }
}