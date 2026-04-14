package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper {

    Content selectById(@Param("id") Long id);

    List<Content> selectCultureList(@Param("offset") int offset, @Param("size") int size);

    Long countCultureList();

    Content selectCultureDetail(@Param("id") Long id);

    int insert(Content content);

    int updateById(Content content);

    int deleteById(@Param("id") Long id);

    void increaseViewCount(@Param("id") Long id);
}
