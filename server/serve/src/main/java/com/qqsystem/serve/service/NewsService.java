package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.News;

import java.util.List;

public interface NewsService {
    News getById(Long id);

    List<News> pageList(int page, int size);

    Long countList();

    boolean add(News news);

    boolean update(News news);

    boolean delete(Long id);

    void increaseView(Long id);
}
