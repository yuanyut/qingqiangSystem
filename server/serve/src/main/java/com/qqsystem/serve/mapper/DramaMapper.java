package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Drama;

import java.util.List;
import java.util.Map;

public interface DramaMapper {
    List<Drama> selectAll();
    Drama selectById(Long id);
    int insert(Drama drama);
    void update(Drama drama);
    void delete(Long id);
    int updateById(Drama drama);
    int deleteById(Long id);
    List<Map<String, Object>> countByCategory();
    List<Map<String, Object>> selectDramaTop10();
    void increaseViewCount(Long id);
    void increaseLikeCount(Long id);
    void decreaseLikeCount(Long id);
    List<Drama> selectList(int offset, int size, Long categoryId, String keyword);
    Long countList(Long categoryId, String keyword);
    int batchDelete(List<Long> ids);
}