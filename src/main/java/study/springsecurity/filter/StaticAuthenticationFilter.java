package study.springsecurity.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  //속성 파일에서 값을 주입할 수 있도록
public class StaticAuthenticationFilter implements Filter {

    @Value("${authorization.key}")  //@Value 어노테이션으로 속성 파일에서 정적 키의 값을 얻음
    private String authorizationKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        String authentication = httpRequest.getHeader("Authorization");

        if (authorizationKey.equals(authentication)) {  //요청의 Authorization 헤더에서 값을 얻고 정적 헤더와 비교
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
