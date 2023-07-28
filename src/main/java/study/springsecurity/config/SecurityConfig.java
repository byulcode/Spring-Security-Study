package study.springsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import study.springsecurity.authentication.filter.InitialAuthenticationFilter;
import study.springsecurity.authentication.filter.JwtAuthenticationFilter;
import study.springsecurity.authentication.provider.OtpAuthenticationProvider;
import study.springsecurity.authentication.provider.UsernamePasswordAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private InitialAuthenticationFilter initialAuthenticationFilter;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private OtpAuthenticationProvider otpAuthenticationProvider;
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 두 맞춤형 필터를 필터 체인에 추가
        http.addFilterAt(
                        initialAuthenticationFilter,
                        BasicAuthenticationFilter.class)
                .addFilterAfter(
                        jwtAuthenticationFilter,
                        BasicAuthenticationFilter.class
                );

        // 모든 요청이 인증되게 함
        http.authorizeHttpRequests()
                .anyRequest().authenticated();

        return http.build();
    }
}
