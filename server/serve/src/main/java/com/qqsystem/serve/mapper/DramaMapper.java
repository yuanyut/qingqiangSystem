package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Drama;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DramaMapper {
    Drama selectById(@Param("id") Long id);

    List<Drama> selectList(@Param("offset") int offset,
                           @Param("size") int size,
                           @Param("categoryId") Long categoryId,
                           @Param("keyword") String keyword);

    Long countList(@Param("categoryId") Long categoryId,
                 @Param("keyword") String keyword);

    int insert(Drama drama);

    int updateById(Drama drama);

    int deleteById(@Param("id") Long id);

    int increaseViewCount(@Param("id") Long id);

    int increaseLikeCount(@Param("id") Long id);

    int decreaseLikeCount(@Param("id") Long id);
}
