package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.Actor;
import com.qqsystem.serve.entity.Drama;
import com.qqsystem.serve.entity.DramaActor;
import com.qqsystem.serve.mapper.ActorMapper;
import com.qqsystem.serve.mapper.DramaActorMapper;
import com.qqsystem.serve.mapper.DramaMapper;
import com.qqsystem.serve.service.ActorService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Resource
    private ActorMapper actorMapper;

    @Resource
    private DramaActorMapper dramaActorMapper;

    @Resource
    private DramaMapper dramaMapper;

    @Override
    public Actor getById(Long id) {
        return actorMapper.selectById(id);
    }

    @Override
    public List<Actor> pageList(int page, int size, String style) {
        int offset = (page - 1) * size;
        return actorMapper.selectList(offset, size, style);
    }

    @Override
    public Long countList(String style) {
        return actorMapper.countList(style);
    }

    @Override
    public boolean add(Actor actor) {
        actor.setWorksCount(0);
        actor.setViewCount(0);
        actor.setLikeCount(0);
        actor.setStatus(1);
        return actorMapper.insert(actor) > 0;
    }

    @Override
    public boolean update(Actor actor) {
        return actorMapper.updateById(actor) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return actorMapper.deleteById(id) > 0;
    }

    @Override
    public void increaseView(Long id) {
        actorMapper.increaseViewCount(id);
    }

    @Override
    public List<Actor> pageListWithRelation(int page, int size, String style, String keyword, Integer status) {
        int offset = (page - 1) * size;
        List<Actor> actors = actorMapper.selectListWithCondition(offset, size, style, keyword, status);
        
        for (Actor actor : actors) {
            if (actor != null) {
                // 查询演员参与的戏剧
                List<DramaActor> dramaActors = dramaActorMapper.selectByActorId(actor.getId());
                List<Drama> dramas = new ArrayList<>();
                if (dramaActors != null && !dramaActors.isEmpty()) {
                    for (DramaActor dramaActor : dramaActors) {
                        if (dramaActor != null) {
                            Drama drama = dramaMapper.selectById(dramaActor.getDramaId());
                            if (drama != null) {
                                dramas.add(drama);
                            }
                        }
                    }
                }
                actor.setDramas(dramas);
            }
        }
        
        return actors;
    }

    @Override
    public Long countList(String style, String keyword, Integer status) {
        return actorMapper.countListWithCondition(style, keyword, status);
    }

    @Override
    public boolean batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        return actorMapper.batchDelete(ids) > 0;
    }

    @Override
    public Actor getDetailWithRelation(Long id) {
        Actor actor = actorMapper.selectById(id);

        if (actor == null) {
            return null;
        }

        // 查询演员参与的戏剧
        List<DramaActor> dramaActors = dramaActorMapper.selectByActorId(id);
        List<Drama> dramas = new ArrayList<>();
        if (dramaActors != null && !dramaActors.isEmpty()) {
            for (DramaActor dramaActor : dramaActors) {
                if (dramaActor != null) {
                    Drama drama = dramaMapper.selectById(dramaActor.getDramaId());
                    if (drama != null) {
                        dramas.add(drama);
                    }
                }
            }
        }
        actor.setDramas(dramas);

        return actor;
    }
}
