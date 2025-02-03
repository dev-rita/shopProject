package com.shop.project.account.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.shop.project.account.dto.AccountJoinRequest;
import com.shop.project.account.dto.AccountLoginRequest;
import com.shop.project.account.etc.AccountConstants;
import com.shop.project.common.util.HttpUtils;
import com.shop.project.member.entity.Member;
import com.shop.project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionAccountHelper implements AccountHelper {

    private final MemberService memberService;

    // 회원가입
    @Override
    public void join(AccountJoinRequest joinReq) {
        //임시수정memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw());
        memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw(), joinReq.getAddress(), joinReq.getPhoneNumber());
    }

    // 로그인
    @Override
    public String login(AccountLoginRequest loginReq, HttpServletRequest req, HttpServletResponse res) {
        Member member = memberService.find(loginReq.getLoginId(), loginReq.getLoginPw());

        // 회원 데이터가 없으면
        if (member == null) {
            return null;
        }

        HttpUtils.setSession(req, AccountConstants.MEMBER_ID_NAME, member.getId());
        return member.getLoginId();
    }

    // 회원 아이디 조회
    @Override
    public Integer getMemberId(HttpServletRequest req) {
        Object memberId = HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);

        if (memberId != null) {
            return (int) memberId;
        }

        return null;
    }

    // 로그인 여부 확인
    @Override
    public boolean isLoggedIn(HttpServletRequest req) {
        return getMemberId(req) != null;
    }

    // 로그아웃
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        HttpUtils.removeSession(req, AccountConstants.MEMBER_ID_NAME);
    }
}