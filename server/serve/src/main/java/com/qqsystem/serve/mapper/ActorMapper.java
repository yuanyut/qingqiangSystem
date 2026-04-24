package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Actor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActorMapper {

    Actor selectById(@Param("id") Long id);

    List<Actor> selectList(@Param("offset") int offset, @Param("size") int size, @Param("style") String style);

    List<Actor> selectListWithCondition(@Param("offset") int offset, @Param("size") int size, @Param("style") String style, @Param("keyword") String keyword, @Param("status") Integer status);

    Long countList(@Param("style") String style);

    Long countListWithCondition(@Param("style") String style, @Param("keyword") String keyword, @Param("status") Integer status);

    int insert(Actor actor);

    int updateById(Actor actor);

    int deleteById(@Param("id") Long id);

    int batchDelete(@Param("ids") List<Long> ids);

    void increaseViewCount(@Param("id") Long id);

    void increaseLikeCount(@Param("id") Long id);

    void decreaseLikeCount(@Param("id") Long id);
}
