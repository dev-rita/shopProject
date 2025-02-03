package com.shop.project.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.shop.project.account.dto.AccountJoinRequest;
import com.shop.project.account.dto.AccountLoginRequest;
import com.shop.project.account.helper.AccountHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.shop.project.common.util.TokenUtils;
import com.shop.project.block.service.BlockService;
import com.shop.project.common.util.HttpUtils;
import com.shop.project.account.etc.AccountConstants;

import java.util.Map;

import com.shop.project.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class AccountController {

    private final AccountHelper accountHelper;
    private final BlockService blockService;
    private final MemberService memberService;

    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequest joinReq) {
        // 입력 값이 비어 있다면
        if (!StringUtils.hasLength(joinReq.getName()) || !StringUtils.hasLength(joinReq.getLoginId()) || !StringUtils.hasLength(joinReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 중복 로그인 아이디가 있으면
        if (memberService.find(joinReq.getLoginId()) != null) { // ③
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.debug("여기???????????????");
        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody AccountLoginRequest loginReq) {
        // 입력 값이 비어 있다면
        if (!StringUtils.hasLength(loginReq.getLoginId()) || !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String output = accountHelper.login(loginReq, req, res);

        if (output == null) { // 로그인 실패 시
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest req) {
        return new ResponseEntity<>(accountHelper.isLoggedIn(req), HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        accountHelper.logout(req, res);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/token")
    public ResponseEntity<?> regenerate(HttpServletRequest req) {
        String accessToken = "";
        String refreshToken = HttpUtils.getCookieValue(req, AccountConstants.REFRESH_TOKEN_NAME);

        // 리프레시 토큰이 유효하다면
        if (StringUtils.hasLength(refreshToken) && TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken)) {
            // 리프레시 토큰의 내부 값 조회
            Map<String, Object> tokenBody = TokenUtils.getBody(refreshToken);

            // 리프레시 토큰의 회원 아이디 조회
            Integer memberId = (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);

            // 액세스 토큰 발급
            accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);
        }

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}