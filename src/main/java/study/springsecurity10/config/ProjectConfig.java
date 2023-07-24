package study.springsecurity10.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import study.springsecurity10.filter.CsrfTokenLogger;

@Configuration
public class ProjectConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
