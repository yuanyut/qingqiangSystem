package com.qqsystem.serve.controller;

import com.qqsystem.serve.common.ResponseResult;
import com.qqsystem.serve.service.RecommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    // 首页推荐
    @GetMapping("/home")
    public ResponseResult<Map<String, ?>> getHomeRecommendations(HttpServletRequest request) {
        // 从JWT中获取用户ID（实际项目中需要根据具体的认证方式实现）
        Long userId = getUserIdFromJWT(request);
        if (userId == null) {
            // 如果用户未登录，使用默认用户ID（或返回热门推荐）
            userId = 1L;
        }
        // 获取首页推荐数据
        Map<String, ?> recommendations = recommendService.getHomeRecommendations(userId);
        // 返回统一格式
        return ResponseResult.success(recommendations);
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
