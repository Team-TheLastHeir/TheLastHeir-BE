package com.heir_project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 100;
    private static final long TIME_WINDOW = 60_000; // 60초

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String ip = req.getRemoteAddr();
        RateLimiter limiter = limiters.computeIfAbsent(ip, k -> new RateLimiter(MAX_REQUESTS, TIME_WINDOW));

        if (!limiter.allowRequest()) {
            ((HttpServletResponse) response).setStatus(429); // Too Many Requests
            response.getWriter().write("요청이 너무 많습니다. 잠시 후 다시 시도하세요.");
            return;
        }

        chain.doFilter(request, response);
    }

    static class RateLimiter {
        private final int maxRequests;
        private final long timeWindow;
        private int requestCount;
        private long windowStart;

        public RateLimiter(int maxRequests, long timeWindow) {
            this.maxRequests = maxRequests;
            this.timeWindow = timeWindow;
            this.windowStart = System.currentTimeMillis();
            this.requestCount = 0;
        }

        synchronized boolean allowRequest() {
            long now = System.currentTimeMillis();
            if (now - windowStart > timeWindow) {
                windowStart = now;
                requestCount = 0;
            }

            if (requestCount < maxRequests) {
                requestCount++;
                return true;
            }
            return false;
        }
    }
}
