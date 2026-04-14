package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Content;

import java.util.List;

public interface ContentService {
    List<Content> getCultureList(int page, int size, String category);

    Long countCultureList(String category);

    Content getCultureDetail(Long id);

    boolean addCulture(Content content);

    boolean updateCulture(Content content);

    boolean deleteCulture(Long id);
}
