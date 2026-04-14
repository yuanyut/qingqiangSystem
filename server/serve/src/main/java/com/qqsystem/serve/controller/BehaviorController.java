package com.qqsystem.serve.controller;

import com.qqsystem.serve.entity.Behavior;
import com.qqsystem.serve.service.BehaviorService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
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
    public Map<String, Object> toggleLike(@RequestParam Long userId, @RequestParam String targetType, @RequestParam Long targetId) {
        Map<String, Object> result = new HashMap<>();
        boolean isLiked = behaviorService.toggleLike(userId, targetType, targetId);
        result.put("success", true);
        result.put("isLiked", isLiked);
        return result;
    }

    // 收藏/取消收藏
    @PostMapping("/favorite/toggle")
    public Map<String, Object> toggleFavorite(@RequestParam Long userId, @RequestParam String targetType, @RequestParam Long targetId) {
        Map<String, Object> result = new HashMap<>();
        boolean isFavorited = behaviorService.toggleFavorite(userId, targetType, targetId);
        result.put("success", true);
        result.put("isFavorited", isFavorited);
        return result;
    }

    // 获取用户点赞列表
    @GetMapping("/like/list")
    public Map<String, Object> getLikeList(@RequestParam Long userId, @RequestParam(required = false) String targetType, @RequestParam int page, @RequestParam int size) {
        Map<String, Object> result = new HashMap<>();
        List<Behavior> list = behaviorService.getLikeList(userId, targetType, page, size);
        result.put("list", list);
        return result;
    }

    // 获取用户收藏列表
    @GetMapping("/favorite/list")
    public Map<String, Object> getFavoriteList(@RequestParam Long userId, @RequestParam(required = false) String targetType, @RequestParam int page, @RequestParam int size) {
        Map<String, Object> result = new HashMap<>();
        List<Behavior> list = behaviorService.getFavoriteList(userId, targetType, page, size);
        result.put("list", list);
        return result;
    }

    // 获取用户浏览历史
    @GetMapping("/browse/list")
    public Map<String, Object> getBrowseHistory(@RequestParam Long userId, @RequestParam(required = false) String targetType, @RequestParam int page, @RequestParam int size) {
        Map<String, Object> result = new HashMap<>();
        List<Behavior> list = behaviorService.getBrowseHistory(userId, targetType, page, size);
        result.put("list", list);
        return result;
    }
}
