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

    // 统一行为接口：添加行为
    @PostMapping("/add")
    public ResponseResult<String> addBehavior(@RequestParam String targetType, @RequestParam Long targetId, @RequestParam String action, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null && !"view".equals(action)) {
            return ResponseResult.badRequest("未登录");
        }
        
        if (userId != null) {
            behaviorService.addBehavior(userId, targetType, targetId, action);
        }
        
        return ResponseResult.success("行为记录成功");
    }

    // 统一行为接口：切换行为状态（点赞/收藏）
    @PostMapping("/toggle")
    public ResponseResult<Map<String, Object>> toggleBehavior(@RequestParam String targetType, @RequestParam Long targetId, @RequestParam String action, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        boolean isActive = behaviorService.toggleBehavior(userId, targetType, targetId, action);
        
        Map<String, Object> result = new HashMap<>();
        if ("like".equals(action)) {
            result.put("isLiked", isActive);
        } else if ("favorite".equals(action)) {
            result.put("isFavorited", isActive);
        }
        
        return ResponseResult.success(result);
    }

    // 查询当前用户行为
    @GetMapping("/my")
    public ResponseResult<Map<String, Object>> getMyBehaviors(@RequestParam(required = false) String targetType, @RequestParam(required = false) String action, @RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseResult.badRequest("未登录");
        }
        
        List<Behavior> list = behaviorService.getUserBehaviorList(userId, targetType, action, page, size);
        Long total = behaviorService.countUserBehavior(userId, targetType, action);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        
        return ResponseResult.success(result);
    }

    // 检查行为状态
    @GetMapping("/check")
    public ResponseResult<Map<String, Object>> checkBehavior(@RequestParam String targetType, @RequestParam Long targetId, @RequestParam String action, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            if ("like".equals(action)) {
                result.put("isLiked", false);
            } else if ("favorite".equals(action)) {
                result.put("isFavorited", false);
            }
        } else {
            boolean exists = behaviorService.isBehaviorExists(userId, targetType, targetId, action);
            if ("like".equals(action)) {
                result.put("isLiked", exists);
            } else if ("favorite".equals(action)) {
                result.put("isFavorited", exists);
            }
        }
        
        return ResponseResult.success(result);
    }
}
