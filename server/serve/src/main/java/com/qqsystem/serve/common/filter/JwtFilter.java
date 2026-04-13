package com.qqsystem.serve.common.filter;

import com.qqsystem.serve.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();
        System.out.println("========== JwtFilter 开始 ==========");
        System.out.println("1. 请求路径: " + uri);
        System.out.println("2. 请求方法: " + request.getMethod());

        // 放行接口
        if (uri.equals("/user/login")
                || uri.equals("/user/register")
                || uri.equals("/admin/login")) {
            System.out.println("3. 命中放行条件，直接放行");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("3. 未命中放行条件，需要验证token");

        // 1. 获取 token
        String token = request.getHeader("Authorization");
        System.out.println("4. Authorization头: [" + token + "]");

        if (token == null || token.isEmpty()) {
            System.out.println("5. token为空，返回401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未登录");
            return;
        }

        // 2. 去掉 Bearer 前缀
        String originalToken = token;
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("5. 去掉Bearer前缀后的token: " + token);
        } else {
            System.out.println("5. token没有Bearer前缀，原样使用: " + token);
        }

        try {
            // 3. 解析 token
            System.out.println("6. 开始解析token...");
            Claims claims = JwtUtil.parseToken(token);
            System.out.println("7. 解析成功!");

            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            System.out.println("8. userId: " + userId + ", username: " + username + ", role: " + role);

            // 4. 放入请求上下文
            request.setAttribute("userId", userId);
            request.setAttribute("username", username);
            request.setAttribute("role", role);

            // 5. 权限控制
            if (uri.startsWith("/admin/") && !"ADMIN".equals(role)) {
                System.out.println("9. 权限不足，需要ADMIN角色，当前角色: " + role);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("无管理员权限");
                return;
            }
            System.out.println("9. 权限验证通过");

            System.out.println("10. 放行请求");
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            System.out.println("6. token解析失败: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token无效或已过期");
        }
        System.out.println("========== JwtFilter 结束 ==========");
    }
}