package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.entity.Comment;
import com.qqsystem.serve.service.CommentService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    // 添加评论
    @PostMapping("/add")
    public ResponseResult<?> addComment(@RequestBody Comment comment, HttpServletRequest request) {
        // 从JWT中获取用户ID（实际项目中需要根据具体的认证方式实现）
        Long userId = getUserIdFromJWT(request);
        if (userId == null) {
            return ResponseResult.unauthorized("用户未登录");
        }
        comment.setUserId(userId);
        boolean success = commentService.addComment(comment);
        if (success) {
            return ResponseResult.success("评论提交成功，等待审核");
        } else {
            return ResponseResult.badRequest("评论提交失败");
        }
    }

    // 查询评论列表（前台，只显示已审核通过的）
    @GetMapping("/list")
    public ResponseResult<List<Comment>> getCommentList(@RequestParam String targetType, @RequestParam Long targetId) {
        List<Comment> comments = commentService.getCommentList(targetType, targetId);
        return ResponseResult.success(comments);
    }

    // 查询评论列表（后台，显示所有状态的）
    @GetMapping("/admin/list")
    public ResponseResult<List<Comment>> getAdminCommentList(@RequestParam String targetType, @RequestParam Long targetId, @RequestParam(required = false) Integer status) {
        List<Comment> comments = commentService.getAdminCommentList(targetType, targetId, status);
        return ResponseResult.success(comments);
    }

    // 审核评论
    @PostMapping("/admin/audit")
    public ResponseResult<?> auditComment(@RequestParam Long commentId, @RequestParam Integer status) {
        boolean success = commentService.auditComment(commentId, status);
        if (success) {
            return ResponseResult.success("审核成功");
        } else {
            return ResponseResult.badRequest("审核失败");
        }
    }

    // 从JWT中获取用户ID（实际项目中需要根据具体的认证方式实现）
    private Long getUserIdFromJWT(HttpServletRequest request) {
        // 这里简化处理，实际项目中需要从JWT token中解析用户ID
        // 例如：String token = request.getHeader("Authorization");
        // 然后解析token获取用户ID
        
        // 暂时返回一个固定的用户ID，用于测试
        return 1L;
    }
}
