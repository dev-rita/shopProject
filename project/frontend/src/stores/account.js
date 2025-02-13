import {defineStore} from 'pinia'

export const useAccountStore = defineStore("account", {
    state: () => ({
        id : "",
        checked: false,
        loggedIn: false,
        accessToken: "",
    }),
    actions: {
        setId(val) {
            this.id = val;
        },
        setChecked(val) {
            this.checked = val;
        },
        setLoggedIn(val) {
            this.loggedIn = val;
        },
        setAccessToken(val) {
            this.accessToken = val;
        },
    },
});