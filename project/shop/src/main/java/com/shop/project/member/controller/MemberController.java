package com.shop.project.member.controller;

import com.shop.project.member.service.MemberService;

import com.shop.project.member.entity.Member;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1")

public class MemberController {
    
      private final MemberService memberService;


 public MemberController(MemberService memberService) {
    this.memberService = memberService;
}
        // 회원 정보 조회 API (GET 방식)
        @GetMapping("/api/account/info")
        public ResponseEntity<?> getMemberInfo(@RequestParam String loginId) {
            // loginId를 요청 파라미터에서 받아옴
            Member member = memberService.find(loginId);
        
            if (member == null) {
                return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);  // 회원이 없으면 404 반환
            }
        
            // 회원 정보 반환 (Map으로 응답)
            Map<String, Object> response = new HashMap<>();
            response.put("member", member);
        
            return new ResponseEntity<>(response, HttpStatus.OK);  // 200 OK와 함께 회원 정보 반환
        }
        

     // 회원 정보 수정 API (주소, 전화번호, 비밀번호 변경)
     @PostMapping("/api/account/update")
     public Member updateMemberInfo(@RequestParam String loginId, 
                                    @RequestParam String newAddress, 
                                    @RequestParam String newPhoneNumber,
                                    @RequestParam(required = false) String newPassword) {  // 비밀번호는 선택적 파라미터로 처리
         return memberService.updateMemberInfo(loginId, newAddress, newPhoneNumber, newPassword);
     }
 
 }