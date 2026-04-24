package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewsMapper {
    News selectById(@Param("id") Long id);

    List<News> selectList(@Param("offset") int offset, @Param("size") int size, @Param("category") Integer category, @Param("keyword") String keyword);

    List<News> selectListWithCondition(@Param("offset") int offset, @Param("size") int size, @Param("category") String category, @Param("keyword") String keyword, @Param("status") Integer status);

    Long countList(@Param("category") Integer category, @Param("keyword") String keyword);

    Long countListWithCondition(@Param("category") String category, @Param("keyword") String keyword, @Param("status") Integer status);

    int insert(News news);

    int updateById(News news);

    int deleteById(@Param("id") Long id);

    int batchDelete(@Param("ids") List<Long> ids);

    void increaseView(@Param("id") Long id);

    void increaseLikeCount(@Param("id") Long id);

    void decreaseLikeCount(@Param("id") Long id);

    // 获取新闻分类统计
    List<Map<String, Object>> selectNewsCategories();

    // 获取头条资讯
    News selectTopNews();

    // 获取热门排行
    List<News> selectHotNews(@Param("size") int size);

    // 获取推荐阅读
    List<News> selectRecommendNews(@Param("size") int size, @Param("category") String category);

    // 获取媒体聚焦（视频类）
    List<News> selectMediaNews(@Param("size") int size);
}
