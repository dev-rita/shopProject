import httpRequester from "@/libs/httpRequester";

// 회원가입
export const join = (args) => {
    return httpRequester.post("/v1/api/account/join", args).catch(e => e.response);
};

// 로그인
export const login = (args) => {
    return httpRequester.post("/v1/api/account/login", args).catch(e => e.response);
};

// 로그인 여부 확인
export const check = () => {
    return httpRequester.get("/v1/api/account/check").catch(e => e.response);
};

// 로그아웃
export const logout = () => {
    return httpRequester.post("/v1/api/account/logout").catch(e => e.response);
};

// 회원 내 정보 조회
export const selectmember = (loginId) => {
    return httpRequester.get(`/v1/api/account/selectmember?loginId=${loginId}`).catch(e => e.response);
};


// 회원 정보 수정
export const updateMemberInfo = (loginId, newAddress, newPhoneNumber, newloginPw) => {
    const data = {
        loginId,
        newAddress,
        newPhoneNumber,
        newloginPw: newloginPw || undefined,  // newPassword 대신 newloginPw로 수정
    };
    return httpRequester.put("/v1/api/account/update", data).catch(e => e.response);
};