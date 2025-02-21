package com.shop.project.member.service;

import com.shop.project.member.entity.Member;

public interface MemberService {

    // 회원 데이터 저장
    void save(String name, String loginId, String loginPw, String address, String phoneNumber);

    // 회원 데이터 조회
    Member find(String loginId, String loginPw);

    // 회원 데이터 조회
    Member find(String loginId);

    // 회원 정보 수정 (주소, 전화번호, 비밀번호 변경)
    Member updateMemberInfo(String loginId, String loginPw,String newloginPw, String newAddress, String newPhoneNumber);
}