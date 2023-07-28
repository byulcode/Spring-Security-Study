package com.example.springsecurity11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {      //데이터베이스에 저장된 암호 해싱 인코더
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();      //애플리케이션이 직접 엔드포인트를 실행할 수 있도록 비활성화
        http.authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
