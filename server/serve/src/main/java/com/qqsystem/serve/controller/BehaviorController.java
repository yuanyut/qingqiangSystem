package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.entity.Behavior;
import com.qqsystem.serve.service.BehaviorService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/behavior")
public class BehaviorController {

    @Resource
    private BehaviorService behaviorService;

    // 点赞/取消点赞
    @PostMapping("/like/toggle")
    public ResponseResult<Map<String, Object>> toggleLike(@RequestParam String targetType, @RequestParam Long targetId, HttpServletRequest request) {
        // 从请求上下文中获取userId（由JWT Filter解析并放入）
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        Map<String, Object> result = new HashMap<>();
        boolean isLiked = behaviorService.toggleLike(userId, targetType, targetId);
        result.put("isLiked", isLiked);
        return ResponseResult.success(result);
    }

    // 收藏/取消收藏
    @PostMapping("/favorite/toggle")
    public ResponseResult<Map<String, Object>> toggleFavorite(@RequestParam String targetType, @RequestParam Long targetId, HttpServletRequest request) {
        // 从请求上下文中获取userId（由JWT Filter解析并放入）
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        Map<String, Object> result = new HashMap<>();
        boolean isFavorited = behaviorService.toggleFavorite(userId, targetType, targetId);
        result.put("isFavorited", isFavorited);
        return ResponseResult.success(result);
    }

    // 获取用户点赞列表
    @GetMapping("/like/list")
    public ResponseResult<Map<String, Object>> getLikeList(@RequestParam(required = false) String targetType, @RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        // 从请求上下文中获取userId（由JWT Filter解析并放入）
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        Map<String, Object> result = new HashMap<>();
        List<Behavior> list = behaviorService.getLikeList(userId, targetType, page, size);
        result.put("list", list);
        return ResponseResult.success(result);
    }

    // 获取用户收藏列表
    @GetMapping("/favorite/list")
    public ResponseResult<Map<String, Object>> getFavoriteList(@RequestParam(required = false) String targetType, @RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        // 从请求上下文中获取userId（由JWT Filter解析并放入）
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        Map<String, Object> result = new HashMap<>();
        List<Behavior> list = behaviorService.getFavoriteList(userId, targetType, page, size);
        result.put("list", list);
        return ResponseResult.success(result);
    }

    // 获取用户浏览历史
    @GetMapping("/browse/list")
    public ResponseResult<Map<String, Object>> getBrowseHistory(@RequestParam(required = false) String targetType, @RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        // 从请求上下文中获取userId（由JWT Filter解析并放入）
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        Map<String, Object> result = new HashMap<>();
        List<Behavior> list = behaviorService.getBrowseHistory(userId, targetType, page, size);
        result.put("list", list);
        return ResponseResult.success(result);
    }
}
