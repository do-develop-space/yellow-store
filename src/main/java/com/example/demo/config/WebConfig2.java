package com.example.demo.config;

import com.example.demo.aop.UnAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Enumeration;

// 간단한 CORS 해결 예제
// @Configuration 주석 해제 후 사용
@Configuration
public class WebConfig2 implements WebMvcConfigurer {

    @Value("${api.v1:/api/v1}")
    String api;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // 요청 정보 로깅
                System.out.println("=== Request Info ===");
                System.out.println("Context Path: " + request.getContextPath());
                System.out.println("Request URI: " + request.getRequestURI());
                System.out.println("Method: " + request.getMethod());
                
                // Authorization 헤더 확인
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                System.out.println("Authorization Header: " + authHeader);
                
                // JWT 토큰 추출 및 검증 (개선된 버전)
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7); // "Bearer " 제거
                    System.out.println("Extracted Token: " + token.substring(0, Math.min(20, token.length())) + "...");
                    
                    // TODO: 실제 JWT 토큰 검증 로직 추가
                    // if (!isValidToken(token)) {
                    //     throw new UnAuthorizationException("Invalid or expired token");
                    // }
                } else if (authHeader != null) {
                    // Bearer가 아닌 경우
                    System.out.println("Warning: Authorization header format is incorrect");
                }
                
                // 모든 요청을 허용 (인증이 필요 없는 경우)
                // 인증이 필요한 경우, 위의 검증 로직에서 false를 반환하거나 예외를 throw
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                // 응답 정보 로깅
                System.out.println("=== Response Info ===");
                System.out.println("Status Code: " + response.getStatus());
                System.out.println("====================\n");
            }
        }).addPathPatterns("/**")  // 모든 경로에 적용
          .excludePathPatterns("/v3/**", "/swagger-ui/**", "/swagger-ui.html");  // Swagger는 제외
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Swagger UI를 위한 CORS 설정
        registry.addMapping("/v3/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
        
        // API 엔드포인트를 위한 CORS 설정
        registry.addMapping(api + "/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Custom-Header")
                .allowCredentials(false)  // credentials를 사용하려면 구체적인 origin을 지정해야 함
                .maxAge(3600);
    }
}
