package study.springsecurity.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class InMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users;

    public InMemoryUserDetailsService(List<UserDetails> users) {
        this.users = users;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return users.stream()
                .filter(    // 사용자 목록에서 요청된 사용자와 이름이 일치하는 항목을 필터링
                        u -> u.getUsername().equals(username)
                )
                .findFirst()    //일치하는 사용자가 있으면 반환
                .orElseThrow(   //이 사용자 이름이 없으면 예외
                        () -> new UsernameNotFoundException("User not Found")
                );
    }
}
