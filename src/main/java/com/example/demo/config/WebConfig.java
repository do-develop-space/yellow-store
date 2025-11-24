package com.example.demo.config;

import com.example.demo.aop.UnAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Enumeration;

// MVC config, CROSS-ORIGN 체크
@Configuration
public class WebConfig {

    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                WebMvcConfigurer.super.addInterceptors(registry);
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        System.out.println(request.getContextPath());
                        System.out.println(request.getHeader(HttpHeaders.AUTHORIZATION));

                        // Spring Security 등으로 JWT 인가 체크
                        request.getContextPath();
                        Enumeration<String> authValue = request.getHeaders(HttpHeaders.AUTHORIZATION);
                        String token = authValue.nextElement().replaceAll("Barear", "");
                        new UnAuthorizationException("UnAuthorized User");
                        // return false or true
                        return HandlerInterceptor.super.preHandle(request, response, handler);
                    }

                    @Override
                    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                        System.out.println(response.getStatus());
                        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
                    }
                });
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v3/**");
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);

//                WebMvcConfigurer.super.addCorsMappings(registry);
            }
        };
    }
}
