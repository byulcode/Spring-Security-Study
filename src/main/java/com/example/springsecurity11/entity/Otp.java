package com.example.springsecurity11.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Otp {
    //인증된 사용자의 생성된 OTP
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;
    private String code;
}
