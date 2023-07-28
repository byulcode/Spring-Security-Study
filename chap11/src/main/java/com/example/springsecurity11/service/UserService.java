package com.example.springsecurity11.service;

import com.example.springsecurity11.entity.Otp;
import com.example.springsecurity11.entity.User;
import com.example.springsecurity11.repository.OtpRepository;
import com.example.springsecurity11.repository.UserRepository;
import com.example.springsecurity11.utils.GenerateCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    @Transactional
    public void addUser(User user) {
        log.info("사용자를 추가하는 메서드 addUser run..");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void auth(User user) {
        log.info("사용자에 대한 OTP를 생성하고 보내는 메서드 auth run..");
        Optional<User> dbUser = userRepository.findUserByUsername(user.getUsername());

        if (dbUser.isPresent()) {   //사용자가 db에 있는 경우 암호 확인
            User u = dbUser.get();
            if (passwordEncoder.matches(
                    user.getPassword(),
                    u.getPassword())) {
                renewOpt(u);    //암호가 맞으면 새 OTP 생성
            } else {
                throw new BadCredentialsException("Bad Credentials");
            }
        } else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Transactional
    public boolean check(Otp otpToValidate) {
        log.info("OTP 검증 메서드 check run..");
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }
        return false;
    }

    private void renewOpt(User user) {
        String code = GenerateCodeUtil.generatedCode();
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(user.getUsername());

        if (userOtp.isPresent()) {  //사용자에 대한 otp가 있을 경우 값 업데이트
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {    //사용자에 대한 otp가 없을 경우 생성
            otpRepository.save(new Otp(user.getUsername(), code));
        }
    }
}
