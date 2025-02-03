package com.shop.project.member.service;

import com.shop.project.member.entity.Member;
import com.shop.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shop.project.common.util.HashUtils;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;

    // 회원 데이터 저장
    @Override
    public void save(String name, String loginId, String loginPw, String address, String phoneNumber) {
        // 솔트 생성
        String loginPwSalt = HashUtils.generateSalt(16);

        // 입력 패스워드에 솔트를 적용
        String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

        // 회원 데이터 저장
        memberRepository.save(new Member(name, loginId, loginPwSalted, loginPwSalt, address, phoneNumber));
    }

    // 회원 데이터 조회
    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String loginPwSalt = member.getLoginPwSalt();
            String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

            if (member.getLoginPw().equals(loginPwSalted)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public Member find(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }

    // 회원 정보 수정 (비밀번호, 주소, 전화번호 변경)
    @Override
    public Member updateMemberInfo(String loginId, String loginPw, String newloginPw, String newPassword, String newAddress, String newPhoneNumber) {
        // 1. 아이디로 회원 정보 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 2. 비밀번호가 변경되었으면 새 비밀번호로 업데이트
        if (newloginPw != null && !newloginPw.isEmpty()) {
            String loginPwSalt = HashUtils.generateSalt(16);
            String loginPwSalted = HashUtils.generateHash(newloginPw, loginPwSalt);
            member.setLoginPw(loginPwSalted);
            member.setLoginPwSalt(loginPwSalt);
        }

        // 3. 새로운 비밀번호로 업데이트
        if (newPassword != null && !newPassword.isEmpty()) {
            String loginPwSalt = HashUtils.generateSalt(16);
            String loginPwSalted = HashUtils.generateHash(newPassword, loginPwSalt);
            member.setLoginPw(loginPwSalted);
            member.setLoginPwSalt(loginPwSalt);
        }

        // 4. 주소와 전화번호 업데이트
        if (newAddress != null && !newAddress.isEmpty()) {
            member.setAddress(newAddress);
        }

        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            member.setPhoneNumber(newPhoneNumber);
        }

        // 5. 수정된 회원 정보 저장
        return memberRepository.save(member);
    }
}
