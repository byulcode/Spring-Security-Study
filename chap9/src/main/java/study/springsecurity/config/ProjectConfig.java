package study.springsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import study.springsecurity.filter.AuthenticationLoggingFilter;
import study.springsecurity.filter.RequestValidationFilter;
import study.springsecurity.filter.StaticAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final StaticAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore(
//                        new RequestValidationFilter(),
//                        BasicAuthenticationFilter.class)
//                .addFilterAfter(
//                        new AuthenticationLoggingFilter(),
//                        BasicAuthenticationFilter.class)
//                .authorizeHttpRequests()
//                .anyRequest().permitAll();


        http
                .addFilterAt(filter, BasicAuthenticationFilter.class)   //필터 체인에서 기본 인증 필터의 위치에 필터 추가
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
