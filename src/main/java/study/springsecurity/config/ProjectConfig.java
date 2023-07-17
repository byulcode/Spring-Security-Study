package study.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("John")
                .password("12345")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("Jane")
                .password("12345")
                .roles("MANAGER")  //roles() 메서드의 매개 변수에는 ROLE_ 접두사를 포함하지 않는다
                .build();

        manager.createUser(user1);  //사용자는 userDetailsService에 의해 추가되고 관리된다.
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeHttpRequests(
                (authz) -> authz.anyRequest()
                        .hasRole("ADMIN")); //엔드포인트에 접근할 수 있는 역할을 지정한다. 여기에 ROLE_ 접두사는 나오지 않는다.

        return http.build();
    }
}
