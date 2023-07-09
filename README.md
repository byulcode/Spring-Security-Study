# Spring-Security
### Chapter 2.
+ 인증과 권한 부여를 위한 기본 구성 요소인 UserDetailsService, PasswordEncoder, AuthenticationProvider을 구현했다.
+ 자격증명(이름, 암호, 하나 이상의 권한)이 있는 사용자 생성한다
+ `NoOpPasswordEncoder` 인스턴스는 암호에 암호화나 해시를 적용하지 않고 일반 텍스트처럼 처리한다. 운영 단계에는 적합하지 않다.
+ AuthenticationProvider을 이용해 애플리케이션의 맞춤형 인증 논리를 구현할 수 있다.
