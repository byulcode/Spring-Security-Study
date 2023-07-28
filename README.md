# Spring-Security-Study
Spring Security 학습용 레파지토리

### Chapter 2.
+ 인증과 권한 부여를 위한 기본 구성 요소인 UserDetailsService, PasswordEncoder, AuthenticationProvider을 구현했다.
+ 자격증명(이름, 암호, 하나 이상의 권한)이 있는 사용자 생성한다
+ `NoOpPasswordEncoder` 인스턴스는 암호에 암호화나 해시를 적용하지 않고 일반 텍스트처럼 처리한다. 운영 단계에는 적합하지 않다.
+ AuthenticationProvider을 이용해 애플리케이션의 맞춤형 인증 논리를 구현할 수 있다.

### Chapter 3. 사용자 관리
+ UserDetails는 스프링 시큐리티에서 사용자를 기술하는 데 이용된다.
+ GrantAuthority 인터페이스로 사용자의 권한을 나타낸다.
+ UserDetailsService 인터페이스는 애플리케이션이 사용자 세부 정보를 얻는 방법을 설명하기 위해 구현해야 하는 계약이다.
+ 스프링 시큐리티는 UserDetailsManager 계약의 여러 구현을 제공한다.
  + InMemoryUserDetailsManger, JdbcDetailsManager, LdapUserDetailsManager가 있다
+ JdbcDetailsManager은 JDBC를 직접 이용하므로 애플리케이션이 다른 프레임워크에 고정되지 않는다는 이점이 있다.

### Chapter 4. 암호 처리
+ PasswordEncoder는 인증 논리에서 암호를 처리하는 가장 중요한 책임을 담당한다.
+ 스프링 시큐리티는 해싱 알고리즘에 여러 대안을 제공하므로 필요한 구현을 선택하기만 하면 된다
  + NoOpPasswordEncoder - 암호를 인코딩하지 않고 일반 텍스트로 유지. 구현 예제로만 사용
  + StandardPasswordEncoder - SHA-256을 이용해 암호를 해시. 구식 방법
  + Pbkdf2PasswordEncoder - PBKDF2를 이용
  + BCryptPasswordEncoder - bcrypt 강력 해싱 함수로 암호를 인코딩함
  + SCryptPasswordEncoder - scrypt 해싱 함수로 암호를 인코딩함
+ DelegatingPasswordEncoder을 사용해 다양한 암호 인코더를 갖추고 특정 구성에 따라 선택하는 방식을 구현할 수 있다.
+ 키 생성기는 암호화 알고리즘에 이용되는 키를 생성하도록 도와주는 유틸리티 객체다
+ 암호기는 데이터 암호화와 복호화를 수행하도록 도와주는 유틸리티 객체다

### Chapter 5. 인증 구현
+ Authentication 인터페이스
  + isAuthenticated() - 인증 프로세스가 끝났으면 true, 아직 진행 중이면 false를 반환
  + getCredentials() - 인증 프로세스에 이용된 암호나 비밀을 반환
  + getAuthorities() - 인증된 요청에 허가된 권한의 컬렉션을 반환
+ AuthenticationProvider은 인증 논리를 처리한다

+ SecurityContext는 Authentication 객체를 저장하는 책임을 가진다.
+ SecurityContext를 관리하는 세가지 전략이 존재한다 - SecurityContextHolder
  + MODE_THREADLOCAL - 각 스레드가 보안 컨텍스트에 각자의 세부 정보를 저장할 수 있게 해준다. 기본 전략이다
  + MODE_INHERITABLETHREADLOCAL - 비동기 메서드의 경우 보안컨텍스트를 다음 스레드로 복사하도록 스프링 시큐리티에 지시한다. 이 방식으로 @Async 메서드를 실행하는 새 스레드가 보안 컨텍스트를 상속하게 할 수 있다.
  + MODE_GLOBAL - 애플리케이션의 모든 스레드가 같은 보안 컨텍스트로 인스턴스를 보게 한ㄷ.

### Chapter 6. 인증 구현 예제 웹 애플리케이션
기본 인증 구현 예제 웹 애플리케이션 구현이다.
+ 사용자 관리 구현 - 암호 인코더 객체 정의, JPA 엔티티 정의, UserDetails 구현 데코레이터, UserDetailsService 구현
+ 맞춤형 인증 논리 구현 - AuthenticationProvider 구현
  + supports() 메서드를 구현해 지원되는 인증 구현 방식을 UsernamePasswordAuthenticationToken로 지정함.
  + authenticate() 메서드는 먼저 사용자 이름이 맞는 사용자를 로드하고, 암호가 데이터베이스에 저장된 해시와 일치하는지 검증함
+ 주 페이지 구현 - Thymeleaf 템플릿을 이용해 로그인한 사용자를 페이지에 표시
  
### Chapter 7. 권한 부여 구성 : 엑섹스 제한
+ 사용자 권한을 기준으로 엔드포인트 접근 제한
  + hasAuthority() - 애플리케이션이 제한을 구성하는 하나의 권한만 매개 변수로 받는다
  + hasAnyAuthority() - 애플리케이션이 제한을 구성하는 권한을 하나 이상 매개 변수로 받을 수 있다
  + access() - SpEL을 기반으로 권한 부여 규칙을 구축하므로 상황에 따라 어떤 규칙도 정의할 수 있다. 하지만 디버그의 어려움이 있으로 사용 권장 X
+ 사용자 역할에 대한 제약 조건 설정
  + hasRole() - 애플리케이션이 요청을 승인할 하나의 역할 이름을 매개 변수로 받는다
  + hasAnyRole() - 애플리케이션이 요청을 승인할 여러 역할 이름을 매개 변수로 받는다.
  + access() - 애플리케이션이 요청을 승인할 역할을 스프링 식으로 지정한다.(hasRole() / hasANyRole()을 SpEL 식으로 이용 가능)
+ 주의사항
  + authorities() 메서드 이용 - [ROLE_] 접두사 붙여주기
  + roles() 메서드 이용 - 접두사 필요 없음

### Chapter 8. 권한 부여 구성 : 제한 적용
특정한 요청 그룹에만 권한 부여 제약 조건을 적용하는 예제
+ 경로와 HTTP 방식에 따라 권한 부여 규칙을 구성할 요청을 지정한다
+ 선택기 메서드로 엔드포인트 선택한다
  + MVC 선택기, 앤트 선택기, 정규식 선택기가 있음
  + Spring Boot3, Spring Security 6.0 부터는 deprecated됨. 대신 requestMatchers() 사용
+ 경로와 HTTP 방식에 따라 권한 부여 규칙을 구성할 요청을 지정한다

+ permitAll() vs authenticated()
  + permitAll() : 나머지 모든 엔드포인트에 대해 모든 요청을 허용한다
  + authenticated() : 인증된 사용자에게만 나머지 모든 요청을 허용한다

### Chapter 11. 실전 : 책임의 분리
+ 클라이언트, 인증 서버, 비즈니스 논리 서버를 설계하고 구현한다
+ 인증서버와 비즈니스 논리 서버를 서로 다른 포트에서 실행해 테스트
  + 인증 서버 테스트 완료
  + 책은 spring boot 3.0이 나오기 전 코드인데, 3.0이상으로 코드를 변환하는 과정에서 오류 발생해 비느지스 논리 서버는 테스트 못함. 추후 수정방법 알아내서 리팩토링 필요 