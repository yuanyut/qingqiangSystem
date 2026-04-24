package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Actor;

import java.util.List;

public interface ActorService {
    Actor getById(Long id);

    Actor getDetailWithRelation(Long id);

    List<Actor> pageList(int page, int size, String style);

    List<Actor> pageListWithRelation(int page, int size, String style, String keyword, Integer status);

    Long countList(String style);

    Long countList(String style, String keyword, Integer status);

    boolean add(Actor actor);

    boolean update(Actor actor);

    boolean delete(Long id);

    boolean batchDelete(List<Long> ids);

    void increaseView(Long id);
}
