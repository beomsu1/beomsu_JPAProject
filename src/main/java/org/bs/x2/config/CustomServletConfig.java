package org.bs.x2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bs.x2.controller.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정 문서로 사용하겠다! 선언
@EnableWebMvc
@Log4j2
@RequiredArgsConstructor
public class CustomServletConfig implements WebMvcConfigurer {

    private final JWTInterceptor jwtInterceptor;

    // 인터셉터 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 인터셉터 추가
        registry.addInterceptor(jwtInterceptor)

                // 어떤경로가 이 인터셉터를 타게할거냐! - 모든 문서
                .addPathPatterns("/api/**")

                // 경로뺴주기 - 로그인은 뺴줄거다
                .excludePathPatterns("/api/member/login" , "/api/member/refresh");
    }
}
