package com.shop.project.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

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

    public Member(){
    }

    //public Member(String name, String loginId, String loginPw, String loginPwSalt) {
     //   this.name = name;
     //   this.loginId = loginId;
     //   this.loginPw = loginPw;
     //  this.loginPwSalt = loginPwSalt;
   //}
   public Member(String name, String loginId, String loginPw, String loginPwSalt, String address, String phoneNumber) {
    this.name = name;
    this.loginId = loginId;
    this.loginPw = loginPw;
    this.loginPwSalt = loginPwSalt;
    this.address = address;
    this.phoneNumber = phoneNumber;
}
   // 비밀번호 수정용 setter 추가
   public void setLoginPw(String loginPw) {
    this.loginPw = loginPw;
}

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
}
