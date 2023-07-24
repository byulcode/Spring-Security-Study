package study.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import study.springsecurity.filter.RequestValidationFilter;

@Configuration
public class ProjectConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(
                        new RequestValidationFilter(),
                        BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
