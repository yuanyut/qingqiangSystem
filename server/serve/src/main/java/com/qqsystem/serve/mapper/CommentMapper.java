package com.qqsystem.serve.mapper;

import com.qqsystem.serve.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    // 添加评论
    int insert(Comment comment);

    // 查询评论列表（前台，只显示已审核通过的）
    List<Comment> selectList(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    // 查询评论列表（后台，显示所有状态的）
    List<Comment> selectAdminList(@Param("targetType") String targetType, @Param("targetId") Long targetId, @Param("status") Integer status);

    // 审核评论
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    // 更新content_stats表的评论计数
    int updateCommentCount(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    // 检查content_stats表是否存在记录
    int countStats(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    // 插入content_stats表记录
    int insertStats(@Param("targetType") String targetType, @Param("targetId") Long targetId);
}
