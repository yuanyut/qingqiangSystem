package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Drama;

import java.util.List;

public interface DramaService {
    Drama getById(Long id);

    Drama getDetailWithRelation(Long id);

    List<Drama> pageList(int page, int size, Long categoryId);

    Long countList(Long categoryId);

    boolean add(Drama drama);

    boolean update(Drama drama);

    boolean delete(Long id);

    void increaseView(Long id);
}
