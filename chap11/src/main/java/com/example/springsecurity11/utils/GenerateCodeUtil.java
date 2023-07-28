package com.example.springsecurity11.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

//OTP 생성
public class GenerateCodeUtil {

    private GenerateCodeUtil() {
    }

    public static String generatedCode() {

        String code;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong(); //임의의 int값 생성하는 인스턴스

            int c = random.nextInt(9000) + 1000;    //임의의 4자리 코드 생성
            code = String.valueOf(c);   // string 으로 변환
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code");
        }
        return code;
    }
}
