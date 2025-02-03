package com.shop.project.member.controller;

import com.shop.project.member.service.MemberService;
import com.shop.project.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
      private final MemberService memberService;

     // 회원 정보 수정 API (주소, 전화번호, 비밀번호 변경)
     @PutMapping("/update")
     public Member updateMemberInfo(@RequestParam String loginId, 
                                    @RequestParam String newAddress, 
                                    @RequestParam String newPhoneNumber,
                                    @RequestParam(required = false) String newPassword) {  // 비밀번호는 선택적 파라미터로 처리
         return memberService.updateMemberInfo(loginId, newAddress, newPhoneNumber, newPassword, newAddress, newPhoneNumber);
     }
 
 }
