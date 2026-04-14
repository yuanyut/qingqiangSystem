package com.qqsystem.serve.controller;

import com.qqsystem.serve.entity.Comment;
import com.qqsystem.serve.service.CommentService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    // 添加评论
    @PostMapping("/add")
    public Map<String, Object> addComment(@RequestBody Comment comment, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        // 从JWT中获取用户ID（实际项目中需要根据具体的认证方式实现）
        Long userId = getUserIdFromJWT(request);
        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }
        comment.setUserId(userId);
        boolean success = commentService.addComment(comment);
        if (success) {
            result.put("success", true);
            result.put("message", "评论提交成功，等待审核");
        } else {
            result.put("success", false);
            result.put("message", "评论提交失败");
        }
        return result;
    }

    // 查询评论列表（前台，只显示已审核通过的）
    @GetMapping("/list")
    public List<Comment> getCommentList(@RequestParam String targetType, @RequestParam Long targetId) {
        return commentService.getCommentList(targetType, targetId);
    }

    // 查询评论列表（后台，显示所有状态的）
    @GetMapping("/admin/list")
    public List<Comment> getAdminCommentList(@RequestParam String targetType, @RequestParam Long targetId, @RequestParam(required = false) Integer status) {
        return commentService.getAdminCommentList(targetType, targetId, status);
    }

    // 审核评论
    @PostMapping("/admin/audit")
    public Map<String, Object> auditComment(@RequestParam Long commentId, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        boolean success = commentService.auditComment(commentId, status);
        if (success) {
            result.put("success", true);
            result.put("message", "审核成功");
        } else {
            result.put("success", false);
            result.put("message", "审核失败");
        }
        return result;
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
