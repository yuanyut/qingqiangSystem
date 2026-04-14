package com.qqsystem.serve.service.impl;

import com.qqsystem.serve.entity.Comment;
import com.qqsystem.serve.mapper.CommentMapper;
import com.qqsystem.serve.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public boolean addComment(Comment comment) {
        // 设置默认状态为待审核
        comment.setStatus(0);
        // 插入评论
        int result = commentMapper.insert(comment);
        if (result > 0) {
            // 检查content_stats表是否存在记录
            int count = commentMapper.countStats(comment.getTargetType(), comment.getTargetId());
            if (count > 0) {
                // 更新评论计数
                commentMapper.updateCommentCount(comment.getTargetType(), comment.getTargetId());
            } else {
                // 插入新记录
                commentMapper.insertStats(comment.getTargetType(), comment.getTargetId());
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Comment> getCommentList(String targetType, Long targetId) {
        return commentMapper.selectList(targetType, targetId);
    }

    @Override
    public List<Comment> getAdminCommentList(String targetType, Long targetId, Integer status) {
        return commentMapper.selectAdminList(targetType, targetId, status);
    }

    @Override
    public boolean auditComment(Long commentId, Integer status) {
        // 验证状态值
        if (status != 1 && status != 2) {
            return false;
        }
        // 更新评论状态
        int result = commentMapper.updateStatus(commentId, status);
        return result > 0;
    }
}
