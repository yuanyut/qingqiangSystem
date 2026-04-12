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


        if (uri.contains("/user/login")) {
            filterChain.doFilter(request, response);
//            System.out.println("token=" );
            return;
        }

        String token = request.getHeader("Authorization");

        if (token != null) {


            if (JwtUtil.isValid(token)) {

                Claims claims = JwtUtil.parseToken(token);

                request.setAttribute("userId", claims.get("userId"));
                request.setAttribute("username", claims.getSubject());

                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(401);
        response.getWriter().write("Token无效或未登录");
    }
}