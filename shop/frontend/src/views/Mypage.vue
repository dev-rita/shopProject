<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAccountStore } from "@/stores/account";
import { updateMemberInfo,info } from "@/services/accountService";  // 기존의 updateMemberInfo를 사용
import axios from 'axios';

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

const fetchMemberInfo = () => {
  const loginId = accountStore.userId;
  console.log("Login ID: ", loginId);  // loginId가 올바르게 출력되는지 확인
  
  axios.post('/v1/api/account/info', { loginId : loginId })
    .then(response => {
      console.log('회원 정보:', response.data);

      // 받은 데이터로 member 객체 업데이트
      member.name = response.data.member.name;
      member.loginId = response.data.member.loginId;
      member.address = response.data.member.address;
      member.phoneNumber = response.data.member.phoneNumber;
    })
    .catch(error => {
      console.error('회원 정보 조회 실패:', error);
    });
}

// 로그인 후 setId 호출 예시
const loginSuccess = () => {
  const userIdFromLogin = 'user123'; // 예시로 로그인 후 받은 ID
  accountStore.setId(userIdFromLogin);  // 로그인 성공 후 setId를 호출하여 userId 설정
};

// 회원 정보 수정 (이름을 updateMemberInfo로 그대로 사용)
const handleUpdateMemberInfo = () => {  // 함수 이름을 변경 (updateMemberInfo를 그대로 사용)
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
      router.push('/profile');  // 프로필 페이지로 이동 (원하는 라우트로 변경)
    })
    .catch(error => {
      console.error('Error updating member info:', error);
      errorMessage.value = 'Failed to update member information.';
    });
};
</script>

<template>
  <div class="update-member-info">
    <h2>회원 정보 수정</h2>

    <!-- 에러 메시지 출력 -->
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>

    <form @submit.prevent="handleUpdateMemberInfo">

      <!-- 로그인 아이디(읽기 전용) -->
      <div>
        <label for="loginId">아이디</label>
        <input type="text" id="loginId" v-model="member.loginId" disabled />
      </div>

      <!-- 주소 수정 -->
      <div>
        <label for="address">주소</label>
        <input type="text" id="address" v-model="member.address" required />
      </div>

      <!-- 전화번호 수정 -->
      <div>
        <label for="phoneNumber">전화번호</label>
        <input type="tel" id="phoneNumber" v-model="member.phoneNumber" required />
      </div>

      <!-- 기존 비밀번호 입력 -->
      <div>
        <label for="loginPw">현재 비밀번호</label>
        <input type="password" id="loginPw" v-model="loginPw" required />
      </div>

      <!-- 새 비밀번호 입력 -->
      <div>
        <label for="newLoginPw">새 비밀번호</label>
        <input type="password" id="newLoginPw" v-model="newLoginPw" />
      </div>

      <!-- 새 비밀번호 확인 -->
      <div>
        <label for="confirmLoginPw">새 비밀번호 확인</label>
        <input type="password" id="confirmLoginPw" v-model="confirmLoginPw" />
      </div>

      <button type="submit" class="submit-btn">정보 수정</button>

    </form>
  </div>
</template>


<style scoped>
/* 화면을 100% 차지하도록 설정 */
html, body {
  height: 100%;
  margin: 0;
}

.update-member-info-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%; /* 부모 요소가 화면을 꽉 차도록 설정 */
  background: #f9f9f9;
}

.update-member-info-card {
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 450px;
  padding: 35px;
  box-sizing: border-box;
  text-align: center;  /* 카드 내부의 요소들이 중앙에 위치하도록 설정 */
  display: flex;
  flex-direction: column;
  justify-content: center; /* 콘텐츠를 세로로 중앙 정렬 */
  align-items: center; /* 가로로 중앙 정렬 */
}

h2 {
  font-family: 'Comic Sans MS', cursive, sans-serif;
  color: #8f3aff;
  font-size: 28px;
  margin-bottom: 25px;
  text-align: center; /* 제목을 중앙에 정렬 */
}

.form-group {
  display: flex;
  flex-direction: column; /* 수직 정렬 */
  align-items: center; /* 수평 중앙 정렬 */
  margin-bottom: 20px;
  width: 100%;  /* form-group이 전체 너비를 차지하게끔 설정 */
}

label {
  font-size: 16px;
  color: #8f3aff;
  font-weight: bold;
  margin-bottom: 5px; /* 라벨과 입력칸 간 간격 설정 */
  text-align: center; /* 라벨도 중앙 정렬 */
  width: 100%;
}

input {
  width: 100%;
  max-width: 350px;
  padding: 12px;
  border-radius: 12px;
  border: 2px solid #A3C8FF;  /* 하늘색 테두리 */
  font-size: 16px;
  color: #333;
  background-color: #DAE8FF; /* 하늘색 배경 */
  transition: border 0.3s ease;
  margin: 0 auto 15px auto; /* 중앙 정렬 및 입력칸 간 간격 설정 */
  display: block; /* block으로 만들어서 수평 중앙 정렬 */
  text-align: center; /* 입력칸 내부 텍스트도 중앙 정렬 */
}

input:focus {
  border-color: #4A90E2; /* 선택 시 더 짙은 파란색 */
  outline: none;
}

/* 버튼 스타일 수정 */
button.submit-btn {
  display: block;  /* 버튼을 block 레벨로 만들어서 중앙 정렬되게 함 */
  width: 100%;
  max-width: 350px;
  padding: 15px;
  background-color: #8f3aff; /* 하늘색 버튼 색상 */
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin: 20px auto; /* 수평 중앙 정렬 */
}

/* 버튼 hover 효과 */
button.submit-btn:hover {
  background-color: #6a2ed8; /* hover 시 진한 보라색으로 변경 */
}

/* 버튼 클릭 시 효과 */
button.submit-btn:active {
  background-color: #5e1fbb; /* 클릭 시 더 진한 색상으로 변경 */
}

/* 에러 메시지 스타일 */
.error-message {
  color: #8f3aff;
  font-size: 16px;
  margin-bottom: 15px;
  text-align: center;
  font-family: 'Comic Sans MS', cursive, sans-serif;
}

@media (max-width: 600px) {
  .update-member-info-card {
    padding: 25px;
  }

 
}
</style>







