<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAccountStore } from "@/stores/account";
import { updateMemberInfo } from "@/services/accountService";  // 기존의 updateMemberInfo를 사용
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

// 회원 정보 불러오기
const fetchMemberInfo = () => {
  const loginId = accountStore.userInfo.loginId; // 상태 관리에서 로그인한 사용자 정보 불러오기
  axios.get(`/members/${loginId}`)
    .then(response => {
      Object.assign(member, response.data);  // 받아온 데이터를 member에 반영
    })
    .catch(error => {
      console.error('Error fetching member info:', error);
      errorMessage.value = 'Failed to load member information.';
    });
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
