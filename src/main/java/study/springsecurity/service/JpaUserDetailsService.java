package study.springsecurity.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.springsecurity.entity.User;
import study.springsecurity.model.CustomUserDetails;
import study.springsecurity.repository.UserRepository;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String username) {

        //예외 인스턴스를 만들기 위한 공급자 선언
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("problem during authentication!");

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(s);
        return new CustomUserDetails(user);
    }
}
