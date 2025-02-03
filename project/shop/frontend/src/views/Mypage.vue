<template>
    <div class="mypage">
      <h2>My Page</h2>
      
      <!-- 회원 정보 표시 -->
      <div v-if="member">
        <div>
          <label for="name">Name:</label>
          <input type="text" v-model="member.name" disabled />
        </div>
        <div>
          <label for="loginId">Login ID:</label>
          <input type="text" v-model="member.loginId" disabled />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" v-model="newPassword" />
        </div>
        <div>
          <label for="address">Address:</label>
          <input type="text" v-model="member.address" />
        </div>
        <div>
          <label for="phoneNumber">Phone Number:</label>
          <input type="text" v-model="member.phoneNumber" />
        </div>
        
        <!-- 수정 버튼 -->
        <button @click="updateMemberInfo">Update Info</button>
      </div>
      
      <!-- 에러 메시지 -->
      <div v-if="errorMessage" class="error">
        {{ errorMessage }}
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        member: null,
        newPassword: '',
        errorMessage: '',
      };
    },
    created() {
      this.fetchMemberInfo();
    },
    methods: {
      // 로그인한 사용자의 정보 불러오기
      fetchMemberInfo() {
        // 로그인된 사용자의 정보를 가져오는 API 호출 (로그인 상태에서 가져오면 됩니다)
        const loginId = 'exampleUser'; // 예시로 설정된 로그인 ID (세션이나 JWT에서 가져오기)
        axios.get(`/members/${loginId}`)
          .then(response => {
            this.member = response.data; // 받아온 데이터로 member 객체 설정
          })
          .catch(error => {
            console.error('Error fetching member info:', error);
            this.errorMessage = 'Failed to load member information.';
          });
      },
      // 회원 정보 수정
      updateMemberInfo() {
        if (!this.member) {
          return;
        }
        
        const updatedInfo = {
          loginId: this.member.loginId,
          newAddress: this.member.address,
          newPhoneNumber: this.member.phoneNumber,
          new: this.newPassword || undefined, // 비밀번호가 있을 경우만 전달
        };
  
        // PUT 요청으로 회원 정보 수정
        axios.put('/members/update', updatedInfo)
          .then(response => {
            alert('Information updated successfully');
          })
          .catch(error => {
            console.error('Error updating member info:', error);
            this.errorMessage = 'Failed to update member information.';
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .mypage {
    width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
  }
  
  .mypage h2 {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .mypage div {
    margin-bottom: 10px;
  }
  
  .mypage label {
    font-weight: bold;
    display: inline-block;
    width: 120px;
  }
  
  .mypage input {
    width: 100%;
    padding: 8px;
    margin-top: 4px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  button {
    width: 100%;
    padding: 10px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  .error {
    color: red;
    text-align: center;
  }
  </style>
  