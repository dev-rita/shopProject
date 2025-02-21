import { defineStore } from 'pinia'

export const useAccountStore = defineStore("account", {
    state: () => ({
        checked: false,
        loggedIn: false,
        accessToken: "",
        userId: "" // userId를 추가하여 로그인한 사용자 ID를 저장
    }),
    actions: {
        setChecked(val) {
            this.checked = val;
        },
        setLoggedIn(val) {
            console.log(val);
            this.loggedIn = val;
        },
        setAccessToken(val) {
            this.accessToken = val;
        },
        setUserId(val) {
            this.userId = val; // userId를 설정하는 액션
        },
         // 새로 추가된 updateUserInfo 액션
         updateUserInfo(data) {
            this.userId = data.userId || this.userId;
            this.accessToken = data.accessToken || this.accessToken;
            this.checked = data.checked !== undefined ? data.checked : this.checked;
            this.loggedIn = data.loggedIn !== undefined ? data.loggedIn : this.loggedIn;
        }
    },
});
