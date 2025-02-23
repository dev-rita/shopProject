<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import {useAccountStore} from "@/stores/account";
import {watch} from "vue";
import {useRoute} from "vue-router";
import {check} from "@/services/accountService";
import { onMounted } from 'vue';

// 계정 스토어
const accountStore = useAccountStore();

// 라우트 객체
const route = useRoute();

// 로그인 여부 확인
const checkAccount = async () => {
  const res = await check();

  if (res.status === 200) {
    accountStore.setChecked(true);
    accountStore.setLoggedIn(res.data === true);
  } else {
    accountStore.setChecked(false);
  }
};

// 커스텀 생성 훅
(async function onCreated() {
  await checkAccount();
})();

// 라우트 경로가 바뀔 때마다 로그인 여부를 확인
watch(() => route.path, () => {
  checkAccount();
});
// 페이지가 로드될 때, 로컬 스토리지에서 토큰과 ID를 가져와서 스토어에 설정
onMounted(() => {
  const savedToken = localStorage.getItem('accessToken');
  const savedUserId = localStorage.getItem('userId');
  
  if (savedToken && savedUserId) {
    accountStore.setAccessToken(savedToken);
    accountStore.setUserId(savedUserId);
    accountStore.setLoggedIn(true);  // 로그인 상태로 설정
  }
});
</script>

<template>
  <!-- 로그인 체크 여부 확인 후 출력-->
  <template v-if="accountStore.checked"> <!-- ③ -->
    <Header/>
    <main>
      <!-- 라우터 뷰 -->
      <router-view></router-view>
    </main>
    <Footer/>
  </template>
</template>