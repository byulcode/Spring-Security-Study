package study.springsecurity.authentication.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import study.springsecurity.authentication.OtpAuthentication;
import study.springsecurity.authentication.UsernamePasswordAuthentication;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;

    @Value("${jwt.signing.key}")    //jwt에 서명하는데 이용한 키
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (code == null) { //HTTP 요청에 OTP가 없으면 사용자 이름과 암호로 인증해야된다고 가정
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(authentication);
        } else {// OTP 코드가 null이 아닌 경우(두 번째 인증 단계)
            Authentication authentication = new OtpAuthentication(username, code);
            authentication = manager.authenticate(authentication);

            SecretKey key = Keys.hmacShaKeyFor(
                    signingKey.getBytes(
                            StandardCharsets.UTF_8));

            // JWT를 구축, 인증된 사용자의 이름을 클레임 중 하나로 저장. 토큰 서명시 key를 이용함
            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();
            response.setHeader("Authorization", jwt);   //토큰을 HTTP 응답 헤더에 추가
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");  // /login 경로에만 이 필터를 적용
    }

}
