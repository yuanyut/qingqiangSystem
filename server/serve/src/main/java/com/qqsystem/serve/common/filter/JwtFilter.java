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

        if (uri.equals("/user/login") || uri.equals("/user/register") || uri.equals("/admin/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token == null) {
            response.setStatus(401);
            response.getWriter().write("未登录");
            return;
        }



        try {
            Claims claims = JwtUtil.parseToken(token);

            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            // ✔ 存入 request（轻量版上下文）
            request.setAttribute("userId", userId);
            request.setAttribute("username", username);
            request.setAttribute("role", role);

            // 权限控制（毕设核心）
            if (uri.startsWith("/admin") && !"ADMIN".equals(role)) {
                response.setStatus(403);
                response.getWriter().write("无管理员权限");
                return;
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("Token无效");
        }
    }
}