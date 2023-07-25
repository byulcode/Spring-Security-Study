package study.springsecurity10.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;

import java.io.IOException;

@Slf4j
public class CsrfTokenLogger implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Object object = request.getAttribute("_csrf");      //_csrf 요청 특성에서 토큰의 값을 얻고 콘솔에 출력함
        CsrfToken token = (CsrfToken) object;

        log.info("CSRF Token : {}", token.getToken());

        chain.doFilter(request, response);
    }
}
