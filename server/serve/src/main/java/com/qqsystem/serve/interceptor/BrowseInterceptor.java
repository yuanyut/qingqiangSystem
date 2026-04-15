package com.qqsystem.serve.interceptor;

import com.qqsystem.serve.service.BehaviorService;
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

    private static final Pattern DRAMA_PATTERN = Pattern.compile("/drama/detail/(\\d+)");
    private static final Pattern NEWS_PATTERN = Pattern.compile("/news/detail/(\\d+)");
    private static final Pattern ACTOR_PATTERN = Pattern.compile("/actor/detail/(\\d+)");
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
            behaviorService.addBehavior(userId, "drama", targetId, "view");
            return true;
        }

        // 检查是否是新闻详情接口
        matcher = NEWS_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
            behaviorService.addBehavior(userId, "news", targetId, "view");
            return true;
        }

        // 检查是否是演员详情接口
        matcher = ACTOR_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
            behaviorService.addBehavior(userId, "actor", targetId, "view");
            return true;
        }

        // 检查是否是文化内容详情接口
        matcher = CULTURE_PATTERN.matcher(path);
        if (matcher.matches()) {
            Long targetId = Long.parseLong(matcher.group(1));
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

    // 从请求中获取用户ID（实际项目中需要根据具体的认证方式实现）
    private Long getUserId(HttpServletRequest request) {
        // 这里简化处理，实际项目中可能从token、session或请求头中获取
        // 例如：String token = request.getHeader("Authorization");
        // 然后解析token获取用户ID
        
        // 暂时返回一个固定的用户ID，用于测试
        return 1L;
    }
}
