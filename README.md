### Chapter11 실전 : 책임의 분리
11장에서는 실제 애플리케이션을 구현해본다.

#### 인증 서버 구현
+ 인증 서버는 사용자 세부 정보를 자체 데이터베이스에 저장한다
+ 인증 중에 사용자를 인증하고 SMS 메시지를 통해 OTP를 전송한다
  + /user/add : 구현을 테스트하기 위해 사용자를 추가
  + /user/auth : 사용자를 인즈하고 OTP가 포함된 SMS를 보냄(SMS를 보내는 부분은 아직 구현X)
  + /otp/check : OTP 값이 인증 서버가 특정 사용자를 위해 이전에 생성한 값인지 검증