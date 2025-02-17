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
        }
    },
});
