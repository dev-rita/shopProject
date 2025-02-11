<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAccountStore } from "@/stores/account";
import { updateMemberInfo, selectmember } from "@/services/accountService"; // selectmember 추가
import axios from 'axios';
import { check } from "@/services/accountService";

// 상태 관리
const accountStore = useAccountStore();
const router = useRouter();

// 상태 변수들
const member = reactive({
  name: '',
  loginId: '',
  address: '',
  phoneNumber: ''
});

const loginPw = ref('');
const newLoginPw = ref('');
const confirmLoginPw = ref('');
const errorMessage = ref('');

// 컴포넌트가 마운트될 때 회원 정보 로드
onMounted(() => {
  fetchMemberInfo();
});


// 로그인 여부 확인 후 회원 정보 조회
const fetchMemberInfo = () => {
  check()  // 로그인 여부 확인
    .then(response => {
      if (response && response.data) {
        const loginId = response.data.loginId;  // 로그인된 사용자의 loginId 가져오기
        
        // 로그인된 사용자라면 회원 정보 조회
        selectmember(loginId)
          .then(memberInfo => {
            if (memberInfo && memberInfo.data) {
              // 받아온 데이터를 member에 반영
              member.name = memberInfo.data.name;
              member.loginId = memberInfo.data.loginId;
              member.address = memberInfo.data.address;
              member.phoneNumber = memberInfo.data.phoneNumber;
            }
          })
          .catch(error => {
            console.error('Error fetching member info:', error);
            errorMessage.value = 'Failed to load member information.';
          });
      }
    })
    .catch(error => {
      console.error('User is not logged in:', error);
      errorMessage.value = 'User is not logged in.';
    });
};

// 회원 정보 수정 함수
const handleUpdateMemberInfo = () => {
  if (!member) {
    return;
  }

  // 에러 메시지 초기화
  errorMessage.value = '';

  // 비밀번호 유효성 검사
  if (newLoginPw.value !== confirmLoginPw.value) {
    errorMessage.value = 'New password and confirmation do not match.';
    return;
  }

  // 기존 비밀번호가 입력되었는지 확인
  if (!loginPw.value) {
    errorMessage.value = 'Current password is required.';
    return;
  }

  // 수정할 정보 준비
  const updatedInfo = {
    loginId: member.loginId,
    loginPw: loginPw.value,      // 기존 비밀번호를 추가
    newAddress: member.address,
    newPhoneNumber: member.phoneNumber,
  };

  // 비밀번호 수정이 있을 경우에만 포함
  if (newLoginPw.value) {
    updatedInfo.newPassword = newLoginPw.value;
  }

  // 서비스 호출하여 서버에 요청
  updateMemberInfo(updatedInfo)  // import한 updateMemberInfo 사용
    .then(response => {
      // 성공 후 상태 업데이트
      accountStore.updateUserInfo(response.data);  // 스토어에 업데이트된 사용자 정보 반영
      alert('Information updated successfully');
      router.push('/mypage');  // 프로필 페이지로 이동 (원하는 라우트로 변경)
    })
    .catch(error => {
      console.error('Error updating member info:', error);
      errorMessage.value = 'Failed to update member information.';
    });
};
</script>

<template>
  <div class="mypage-container">
    <h1>My Page</h1>

    <!-- 에러 메시지 표시 -->
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

    <!-- 회원 정보 표시 -->
    <div v-if="member.name">
      <div class="member-info">
        <h2>회원 정보</h2>
        <div>
          <label>이름: </label>
          <span>{{ member.name }}</span>
        </div>
        <div>
          <label>아이디: </label>
          <span>{{ member.loginId }}</span>
        </div>
        <div>
          <label>주소: </label>
          <input v-model="member.address" type="text" placeholder="주소를 수정하세요">
        </div>
        <div>
          <label>전화번호: </label>
          <input v-model="member.phoneNumber" type="text" placeholder="전화번호를 수정하세요">
        </div>
      </div>

      <!-- 비밀번호 변경 섹션 -->
      <div class="password-change">
        <h3>비밀번호 변경</h3>
        <div>
          <label>현재 비밀번호: </label>
          <input v-model="loginPw" type="password" placeholder="현재 비밀번호">
        </div>
        <div>
          <label>새 비밀번호: </label>
          <input v-model="newLoginPw" type="password" placeholder="새 비밀번호">
        </div>
        <div>
          <label>새 비밀번호 확인: </label>
          <input v-model="confirmLoginPw" type="password" placeholder="새 비밀번호 확인">
        </div>
      </div>

      <!-- 회원 정보 수정 버튼 -->
      <div class="update-buttons">
        <button @click="handleUpdateMemberInfo">정보 수정</button>
      </div>
    </div>

    <!-- 로그인되지 않은 경우 -->
    <div v-else>
      <p>로그인된 사용자가 없습니다. 로그인 후 다시 시도해주세요.</p>
    </div>
  </div>
</template>