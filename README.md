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