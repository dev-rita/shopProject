package com.shop.project.member.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HashUtils{

      // 비밀번호 해시화 메서드 (salt와 결합하여 SHA-256 해시 생성)
      public static String generateHash(String loginPw, String loginPwSalt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(loginPwSalt.getBytes(StandardCharsets.UTF_8));  // salt 값을 해시기에 추가
            byte[] hashedBytes = md.digest(loginPw.getBytes(StandardCharsets.UTF_8));  // 비밀번호를 해시화
            return Base64.getEncoder().encodeToString(hashedBytes);  // 해시 결과를 Base64로 인코딩하여 반환
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not found", e);  // 예외 처리
        }
    }

    // 랜덤 salt 생성 메서드
    public static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);  // Base64로 인코딩하여 반환
    }
}