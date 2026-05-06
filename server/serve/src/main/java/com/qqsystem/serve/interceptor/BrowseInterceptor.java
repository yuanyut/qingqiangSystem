package com.qqsystem.serve.interceptor;

import com.qqsystem.serve.common.utils.JwtUtil;
import com.qqsystem.serve.service.BehaviorService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BrowseInterceptor implements HandlerInterceptor {

    @Resource
    private BehaviorService behaviorService;

    private static final Pattern DRAMA_PATTERN = Pattern.compile("/drama/detail/full/(\\d+)");
    private static final Pattern NEWS_PATTERN = Pattern.compile("/news/detail/(\\d+)");
    private static final Pattern ACTOR_PATTERN = Pattern.compile("/actor/detail/full/(\\d+)");
    private static final Pattern CULTURE_PATTERN = Pattern.compile("/culture/detail/(\\d+)");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求中获取用户ID（这里假设用户ID存储在请求属性中，实际项目中可能从token或session中获取）
        Long userId = getUserId(request);
        if (userId == null) {
            // 如果没有用户ID，不记录浏览记录
            return true;
        }

        String path = request.getRequestURI();
        Matcher matcher;

        // 检查是否是戏剧详情接口
        matcher = DRAMA_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
            System.out.println("[浏览记录] 用户ID: " + userId + ", 类型: drama, 目标ID: " + targetId + ", 路径: " + path + ", 时间: " + java.time.LocalDateTime.now());
            behaviorService.addBehavior(userId, "drama", targetId, "view");
            return true;
        }

        // 检查是否是新闻详情接口
        matcher = NEWS_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
            System.out.println("[浏览记录] 用户ID: " + userId + ", 类型: news, 目标ID: " + targetId + ", 路径: " + path + ", 时间: " + java.time.LocalDateTime.now());
            behaviorService.addBehavior(userId, "news", targetId, "view");
            return true;
        }

        // 检查是否是演员详情接口
        matcher = ACTOR_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
            System.out.println("[浏览记录] 用户ID: " + userId + ", 类型: actor, 目标ID: " + targetId + ", 路径: " + path + ", 时间: " + java.time.LocalDateTime.now());
            behaviorService.addBehavior(userId, "actor", targetId, "view");
            return true;
        }

        // 检查是否是文化内容详情接口
        matcher = CULTURE_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
            System.out.println("[浏览记录] 用户ID: " + userId + ", 类型: culture, 目标ID: " + targetId + ", 路径: " + path + ", 时间: " + java.time.LocalDateTime.now());
            behaviorService.addBehavior(userId, "culture", targetId, "view");
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 不需要处理
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 不需要处理
    }

    // 从请求中获取用户ID（优先从请求属性获取，其次从Token解析）
    private Long getUserId(HttpServletRequest request) {
        // 1. 优先从请求属性中获取（由JwtFilter设置）
        Object userIdObj = request.getAttribute("userId");
        if (userIdObj instanceof Long) {
            return (Long) userIdObj;
        }
        
        // 2. 从请求头中的Token解析
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Claims claims = JwtUtil.parseToken(token);
                return claims.get("userId", Long.class);
            } catch (Exception e) {
                // Token解析失败，可能是未登录用户
                return null;
            }
        }
        
        return null;
    }
}
