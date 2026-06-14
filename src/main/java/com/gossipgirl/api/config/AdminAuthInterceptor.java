package com.gossipgirl.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Value("${admin.secret-token}")
    private String adminSecret;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String token = request.getHeader("X-Admin-Token");
        if (token == null || !token.equals(adminSecret)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Access denied. XOXO\"}");
            return false;
        }
        return true;
    }
}