package com.shop.project.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountJoinRequest {

    private String name;
    private String loginId;
    private String loginPw;
    private String address;
    private String phoneNumber;
}