package com.qqsystem.serve.service;

import com.qqsystem.serve.entity.Comment;

import java.util.List;

public interface CommentService {
    // 添加评论
    boolean addComment(Comment comment);

    // 查询评论列表（前台，只显示已审核通过的）
    List<Comment> getCommentList(String targetType, Long targetId);

    // 查询评论列表（后台，显示所有状态的）
    List<Comment> getAdminCommentList(String targetType, Long targetId, Integer status);

    // 审核评论
    boolean auditComment(Long commentId, Integer status);
}
