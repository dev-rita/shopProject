<template>
  <footer class="py-5 bg-dark text-white">
    <div class="container">
      <div class="row">
        <!-- 첫 번째 컬럼: 고객센터 관련 항목들 -->
        <div class="col-md-3">
          <h5>📞 고객센터</h5>  <!-- 전화 아이콘 이모지 추가 -->
          <ul class="list-unstyled">
            <li><router-link to="/notice">📝 공지사항</router-link></li>
            <li><router-link to="/service">서비스</router-link></li>
            <li><router-link to="/affiliate-card">제휴카드</router-link></li>
            <li><router-link to="/viewer-download">뷰어 다운로드</router-link></li>
            <li><router-link to="/other-inquiries">기타 문의</router-link></li>
            <li><router-link to="/content-supply-inquiries">콘텐츠 제공 문의</router-link></li>
            <li><router-link to="/business-partnership-inquiries">사업 제휴 문의</router-link></li>
          </ul>
        </div>

        <!-- 두 번째 컬럼: 회사 관련 항목들 -->
        <div class="col-md-3">
          <h5>회사</h5>
          <ul class="list-unstyled">
            <li><router-link to="/about-us">회사 소개</router-link></li>
            <li><router-link to="/careers">인재채용</router-link></li>
          </ul>
        </div>

        <!-- 저작권 및 기타 정보 -->
        <div class="col-md-6 text-center">
          <div>&copy; 2024. Example. All rights reserved.</div>
        </div>
      </div>
    </div>
  </footer>
</template>
