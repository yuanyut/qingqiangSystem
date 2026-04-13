package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Actor;

import java.util.List;

public interface ActorService {
    Actor getById(Long id);

    Actor getDetailWithRelation(Long id);

    List<Actor> pageList(int page, int size);

    Long countList();

    boolean add(Actor actor);

    boolean update(Actor actor);

    boolean delete(Long id);

    void increaseView(Long id);
}
