package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Drama;
import com.qqsystem.serve.entity.DramaCategory;

import java.util.List;

public interface DramaService {
    Drama getById(Long id);
    
    List<DramaCategory> getCategoryList();

    Drama getDetailWithRelation(Long id);

    List<Drama> pageList(int page, int size, Long categoryId, String keyword);
    
    List<Drama> pageListWithRelation(int page, int size, Long categoryId, String keyword, boolean includeAllStatus);

    Long countList(Long categoryId, String keyword, boolean includeAllStatus);

    boolean add(Drama drama);

    boolean update(Drama drama);

    boolean delete(Long id);

    boolean batchDelete(List<Long> ids);

    void increaseView(Long id);
}
