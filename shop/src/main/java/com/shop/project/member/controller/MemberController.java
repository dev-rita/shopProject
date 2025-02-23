package com.shop.project.member.controller;

import com.shop.project.member.service.MemberService;

import lombok.extern.log4j.Log4j2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.project.member.entity.Member;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1")
@Log4j2
public class MemberController {
    
      private final MemberService memberService;


 public MemberController(MemberService memberService) {
    this.memberService = memberService;
}
        // 회원 정보 조회 API (GET 방식)
        @PostMapping("/api/account/info")
        public ResponseEntity<?> getMemberInfo(@RequestBody Map<String, Object> param) {

            log.info("param : {}" , param.toString());
            String loginId = param.get("loginId").toString();
           // String loginId = Objects.toString(param.get("loginId"), "");
            // loginId를 요청 파라미터에서 받아옴
            Member member = memberService.find(loginId);
        
            if (member == null) {
                return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);  // 회원이 없으면 404 반환
            }

            member.setLoginPw("");
            member.setLoginPwSalt("");

            // 회원 정보 반환 (Map으로 응답)
            Map<String, Object> response = new HashMap<>();
            response.put("member", member);
        
            return new ResponseEntity<>(response, HttpStatus.OK);  // 200 OK와 함께 회원 정보 반환
        }
        

     // 회원 정보 수정 API (주소, 전화번호, 비밀번호 변경)
     @PutMapping("/api/account/update")
    public ResponseEntity<?> updateMemberInfo(@RequestBody Map<String, Object> param) {

    log.info("param : {}" , param.toString());

    // 파라미터 값 추출
    String name = param.get("name").toString();
    String loginId = param.get("loginId").toString();
    String loginPw = param.get("loginPw").toString();
    String newAddress = param.get("newAddress").toString();
    String newPhoneNumber = param.get("newPhoneNumber").toString();
    String newLoginPw = param.get("newLoginPw").toString();

    try {
        // URL 디코딩 처리 (UTF-8로 디코딩)
        newAddress = URLDecoder.decode(newAddress, StandardCharsets.UTF_8);
        newPhoneNumber = URLDecoder.decode(newPhoneNumber, StandardCharsets.UTF_8);
    } catch (Exception e) {
        log.error("URL decoding failed: ", e);
        return new ResponseEntity<>("Error decoding address or phone number", HttpStatus.BAD_REQUEST);
    }

    // MemberService를 통해 회원 정보 수정
    Member member = memberService.updateMemberInfo(name, loginId, loginPw, newAddress, newPhoneNumber, newLoginPw);

    // 수정된 회원 정보 반환
    Map<String, Object> response = new HashMap<>();
    response.put("member", member);

    return new ResponseEntity<>(response, HttpStatus.OK);  // 200 OK와 함께 회원 정보 반환
}
 }