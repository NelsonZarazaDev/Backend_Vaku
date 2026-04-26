package com.Vaku.Vaku.configs;

import com.Vaku.Vaku.security.interceptor.AuditTrailInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuditInterceptorConfig implements WebMvcConfigurer {
    private final AuditTrailInterceptor auditTrailInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditTrailInterceptor).addPathPatterns("/**");
    }
}
