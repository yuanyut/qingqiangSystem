package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.*;
import com.qqsystem.serve.mapper.*;
import com.qqsystem.serve.service.DramaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DramaServiceImpl implements DramaService {

    private static final Logger logger = LoggerFactory.getLogger(DramaServiceImpl.class);

    @Resource
    private DramaMapper dramaMapper;

    @Resource
    private DramaActorMapper dramaActorMapper;

    @Resource
    private DramaContentMapper dramaContentMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private ActorMapper actorMapper;

    @Override
    public Drama getById(Long id) {
        return dramaMapper.selectById(id);
    }

    // ⭐ 核心增强方法
    @Override
    public Drama getDetailWithRelation(Long id) {
        logger.info("开始查询戏剧详情，ID: {}", id);

        Drama drama = dramaMapper.selectById(id);
        logger.info("查询到戏剧基本信息: {}", drama);

        if (drama == null) {
            logger.info("戏剧不存在，ID: {}", id);
            return null;
        }

        // actors - 处理空表情况
        List<DramaActor> actors = dramaActorMapper.selectByDramaId(id);
        logger.info("查询到戏剧演员关联: {}", actors);
        if (actors == null || actors.isEmpty()) {
            logger.info("没有找到演员关联数据");
            drama.setActors(new ArrayList<>());
            drama.setActorDetails(new ArrayList<>());
        } else {
            List<DramaActor> validActors = new ArrayList<>();
            List<Actor> actorDetails = new ArrayList<>();
            for (DramaActor actor : actors) {
                if (actor != null) {
                    validActors.add(actor);
                    // 查询完整的演员信息
                    logger.info("查询演员信息，ID: {}", actor.getActorId());
                    Actor actorDetail = actorMapper.selectById(actor.getActorId());
                    logger.info("查询到演员信息: {}", actorDetail);
                    if (actorDetail != null) {
                        actorDetails.add(actorDetail);
                    }
                }
            }
            drama.setActors(validActors);
            drama.setActorDetails(actorDetails);
            logger.info("设置演员信息: {}, 演员详情: {}", validActors, actorDetails);
        }

        // contents
        List<DramaContent> relations = dramaContentMapper.selectByDramaId(id);
        logger.info("查询到戏剧内容关联: {}", relations);
        List<Content> contents = new ArrayList<>();
        for (DramaContent dc : relations) {
            if (dc != null) {
                logger.info("查询内容信息，ID: {}", dc.getContentId());
                Content c = contentMapper.selectById(dc.getContentId());
                logger.info("查询到内容信息: {}", c);
                if (c != null) {
                    contents.add(c);
                }
            }
        }
        drama.setContents(contents);
        logger.info("设置内容信息: {}", contents);

        logger.info("查询完成，返回戏剧详情: {}", drama);
        return drama;
    }
    @Override
    public List<Drama> pageList(int page, int size, Long categoryId, String keyword) {
        int offset = (page - 1) * size;
        return dramaMapper.selectList(offset, size, categoryId, keyword);
    }
    
    @Override
    public List<Drama> pageListWithRelation(int page, int size, Long categoryId, String keyword) {
        int offset = (page - 1) * size;
        List<Drama> dramas = dramaMapper.selectList(offset, size, categoryId, keyword);
        
        for (Drama drama : dramas) {
            if (drama != null) {
                // 查询关联的演员
                List<DramaActor> actors = dramaActorMapper.selectByDramaId(drama.getId());
                List<DramaActor> validActors = new ArrayList<>();
                List<Actor> actorDetails = new ArrayList<>();
                for (DramaActor actor : actors) {
                    if (actor != null) {
                        validActors.add(actor);
                        // 查询完整的演员信息
                        Actor actorDetail = actorMapper.selectById(actor.getActorId());
                        if (actorDetail != null) {
                            actorDetails.add(actorDetail);
                        }
                    }
                }
                drama.setActors(validActors);
                drama.setActorDetails(actorDetails);
                
                // 查询关联的内容
                List<DramaContent> relations = dramaContentMapper.selectByDramaId(drama.getId());
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
            }
        }
        
        return dramas;
    }

    @Override
    public Long countList(Long categoryId, String keyword) {
        return dramaMapper.countList(categoryId, keyword);
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
    public boolean batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        return dramaMapper.batchDelete(ids) > 0;
    }

    @Override
    public void increaseView(Long id) {
        dramaMapper.increaseViewCount(id);
    }
}