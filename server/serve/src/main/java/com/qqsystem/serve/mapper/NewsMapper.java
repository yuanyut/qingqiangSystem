package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsMapper {

    News selectById(@Param("id") Long id);

    List<News> selectList(@Param("offset") int offset, @Param("size") int size);

    Long countList();

    int insert(News news);

    int updateById(News news);

    int deleteById(@Param("id") Long id);

    void increaseViewCount(@Param("id") Long id);
}
