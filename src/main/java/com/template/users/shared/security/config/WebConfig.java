package com.template.users.shared.security.config;

import com.template.users.shared.security.interceptors.SecurityAnnotationInterceptor;
import com.template.users.shared.security.resolvers.AuthenticatedUserResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SecurityAnnotationInterceptor securityAnnotationInterceptor;
    private final AuthenticatedUserResolver authenticatedUserResolver;

    public WebConfig(SecurityAnnotationInterceptor securityAnnotationInterceptor,
                    AuthenticatedUserResolver authenticatedUserResolver) {
        this.securityAnnotationInterceptor = securityAnnotationInterceptor;
        this.authenticatedUserResolver = authenticatedUserResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityAnnotationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**", "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedUserResolver);
    }
}