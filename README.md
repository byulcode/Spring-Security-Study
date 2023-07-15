# Spring-Security
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