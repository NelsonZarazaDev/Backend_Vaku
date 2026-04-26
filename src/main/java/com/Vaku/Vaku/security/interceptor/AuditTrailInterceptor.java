package com.Vaku.Vaku.security.interceptor;

import com.Vaku.Vaku.apiRest.service.AuditLogsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuditTrailInterceptor implements HandlerInterceptor {
    private static final Set<String> AUDITED_METHODS = Set.of("POST", "PUT", "PATCH", "DELETE");
    private static final String START_TIME_ATTR = "AUDIT_START_TIME";

    private final AuditLogsService auditLogsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        if (!AUDITED_METHODS.contains(request.getMethod())) {
            return;
        }

        Object value = request.getAttribute(START_TIME_ATTR);
        long startTime = value instanceof Long ? (Long) value : System.currentTimeMillis();
        long durationMs = System.currentTimeMillis() - startTime;

        auditLogsService.register(request, response, durationMs);
    }
}
