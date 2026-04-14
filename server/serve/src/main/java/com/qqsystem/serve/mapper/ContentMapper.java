package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContentMapper {

    Content selectById(@Param("id") Long id);

    List<Content> selectCultureList(@Param("offset") int offset, @Param("size") int size, @Param("category") String category);

    Long countCultureList(@Param("category") String category);

    Content selectCultureDetail(@Param("id") Long id);

    int insert(Content content);

    int updateById(Content content);

    int deleteById(@Param("id") Long id);

    void increaseViewCount(@Param("id") Long id);

    // 获取文化资讯分类统计
    List<Map<String, Object>> selectCultureCategories();

    // 获取头条资讯
    Content selectTopCulture();

    // 获取热门排行
    List<Content> selectHotCulture(@Param("size") int size);

    // 获取推荐阅读
    List<Content> selectRecommendCulture(@Param("size") int size);

    // 获取媒体聚焦（视频类）
    List<Content> selectMediaCulture(@Param("size") int size);
}
