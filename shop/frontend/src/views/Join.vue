<script setup>
import { reactive } from "vue";
import { join } from "@/services/accountService";
import { useRouter } from "vue-router";

// 반응형 상태
const state = reactive({
  form: {
    name: "",
    loginId: "",
    loginPw: "",
    address: "", // New field for address
    phoneNumber: "" // New field for phone number
  }
});

// 라우터 객체
const router = useRouter();

// 회원가입 데이터 제출
const submit = async () => {
  if (!state.form.name?.trim()) {
    window.alert("이름을 입력해주세요.");
    document.getElementById("name")?.focus();
    return;
  } else if (!state.form.loginId?.trim()) {
    window.alert("이메일을 입력해주세요.");
    document.getElementById("loginId")?.focus();
    return;
  } else if (!state.form.loginPw?.trim()) {
    window.alert("패스워드를 입력해주세요.");
    document.getElementById("loginPw")?.focus();
    return;
  } else if (!state.form.address?.trim()) {
    window.alert("주소를 입력해주세요.");
    document.getElementById("address")?.focus();
    return;
  } else if (!state.form.phoneNumber?.trim()) {
    window.alert("전화번호를 입력해주세요.");
    document.getElementById("phoneNumber")?.focus();
    return;
  }

  // Optionally, you can add phone number validation here (e.g., regex for format)

  const res = await join(state.form);

  if (res.status === 200) {
    window.alert("회원가입을 완료했습니다.");
    await router.push("/");
  } else if (res.status === 409) {
    window.alert("이미 사용 중인 이메일입니다. 다른 값을 입력해주세요.");
    document.getElementById("loginId")?.focus();
  }
};
</script>

<template>
  <div class="join">
    <div class="container">
      <form class="py-5 d-flex flex-column gap-3" @submit.prevent="submit">
        <h1 class="h5 mb-3">회원가입</h1>

        <!-- Name -->
        <div class="form-floating">
          <input type="text" class="form-control" id="name" placeholder="이름" v-model="state.form.name">
          <label for="name">이름</label>
        </div>

        <!-- Email -->
        <div class="form-floating">
          <input type="email" class="form-control" id="loginId" placeholder="이메일" v-model="state.form.loginId">
          <label for="loginId">이메일</label>
        </div>

        <!-- Password -->
        <div class="form-floating">
          <input type="password" class="form-control" id="loginPw" placeholder="패스워드" v-model="state.form.loginPw">
          <label for="loginPw">패스워드</label>
        </div>

        <!-- Address -->
        <div class="form-floating">
          <input type="text" class="form-control" id="address" placeholder="주소" v-model="state.form.address">
          <label for="address">주소</label>
        </div>

        <!-- Phone Number -->
        <div class="form-floating">
          <input type="text" class="form-control" id="phoneNumber" placeholder="전화번호" v-model="state.form.phoneNumber">
          <label for="phoneNumber">전화번호</label>
        </div>

        <!-- Submit Button -->
        <button type="submit" class="w-100 h6 btn py-3 btn-primary">회원가입</button>
      </form>
    </div>
  </div>
</template>
