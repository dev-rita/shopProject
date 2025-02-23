package com.shop.project.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import com.shop.project.member.service.HashUtils;  // 정확한 패키지 경로로 수정
 // HashUtils import 추가

@Setter
@Getter
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(length = 64, nullable = false)
    private String loginPw;

    @Column(length = 16, nullable = false)
    private String loginPwSalt;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    // 새로 추가된 필드들
    @Column(length = 255)
    private String address;  // 주소

    @Column(length = 15)
    private String phoneNumber;  // 폰번호

    // 기본 생성자
    public Member() {}

    // 회원가입 시, 주소와 전화번호를 포함하는 생성자
    public Member(String name, String loginId, String loginPw, String loginPwSalt, String address, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.loginPwSalt = loginPwSalt;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // 비밀번호 해시화 (기존 비밀번호를 저장할 때 해시 처리)
    public void setLoginPw(String loginPw) {
        this.loginPw = HashUtils.generateHash(loginPw, this.loginPwSalt);  // 비밀번호 해시화
    }

    // 비밀번호 검증 메서드
    public boolean checkPassword(String password) {
        String hashedPassword = HashUtils.generateHash(password, this.loginPwSalt);  // 입력된 비밀번호 해시화
        return this.loginPw.equals(hashedPassword);  // 저장된 해시값과 비교
    }

    // 비밀번호 salt 설정
    public void setLoginPwSalt(String loginPwSalt) {
        this.loginPwSalt = loginPwSalt;
    }

    // 주소와 전화번호 setter 추가
    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
}
