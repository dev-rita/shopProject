package com.shop.project.member.controller;

import com.shop.project.member.service.MemberService;
import com.shop.project.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
      private final MemberService memberService;


        // 회원 정보 조회 API (GET 방식)
    @GetMapping("/info")
    public Member getMemberInfo(@RequestParam String loginId) {
        // loginId로 회원 정보를 조회
        Member member = memberService.find(loginId);
        
        if (member == null) {
            throw new RuntimeException("Member not found");  // 회원이 없으면 예외 처리
        }

        return member;  // 조회된 회원 정보 반환
    }

     // 회원 정보 수정 API (주소, 전화번호, 비밀번호 변경)
     @PostMapping("/update")
     public Member updateMemberInfo(@RequestParam String loginId, 
                                    @RequestParam String newAddress, 
                                    @RequestParam String newPhoneNumber,
                                    @RequestParam(required = false) String newPassword) {  // 비밀번호는 선택적 파라미터로 처리
         return memberService.updateMemberInfo(loginId, newAddress, newPhoneNumber, newPassword);
     }
 
 }