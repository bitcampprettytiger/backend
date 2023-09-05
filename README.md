# SpringBoot-Project-먹자취
스프링부트 + JPA 노점(길거리 음식) 서비스
## 📺프로젝트 소개
편의성과 높은 품질의 노점(길거리 음식)을 결합한 웹앱 서비스.</br>
이 서비스를 통해 사용자는 다양한 지역의 합법적인 음식 노점을 쉽고 빠르게 찾을 수 있으며,</br> 포장 주문과 줄 서기 없는 편리한 이용을 경험할 수 있다.
## ⌚개발 기간
- 2023-07-24 ~ 2023-09-11

### 👫멤버 구성
- 👨‍🦱팀장 신민규
  
      판매자, 유저 로그인 회원가입 ( Access Token), 지도(카카오 맵API 리스트, 마커 생성) 상세 페이지 지도 구현

- 👦팀원 심봉교
  
      cloud db mysql 구축, cloud object storage 연결, jwt(access token refresh token) 로그인 기능, cloud test server구축,
      위치기반 vendor 검색 구축 ,socket(node.js 이용 (줄서기,포장,알림)), 협업툴 구축(노션,git repo, jira)
 
- 👧팀원 정유진
  
      현재위치API, 홈화면 UI구현(카테고리별 인기 페이지), 검색페이지 UI구현, 마이페이지 UI구현(리뷰,즐겨찾기,주문내역,설정)
  
- 👩팀원 한은희
 
      가게 상세 UI 구현(각 가게 정보 표출), 전체 디자인 담당 및 수정, 장바구니 UI 구현, 결제 API
  
- 🧑‍🦱팀원 이정수
  
      도로점유 허가증 API(조회 및 검증), 리뷰 관련 로직 구현(가게별 조회 , 등록 , 삭제)

- 👩‍🦰팀원 이영은

      노점 관련 API 로직(조회 및 검증), 사업자 등록증 API(조회 및 검증), 찜하기 기능, 마이페이지,
      가게타입/조회수 기준 상위 아이템 추출 기능

- 👱‍♀️팀원 박지해
  
      가게 및 메뉴관련 CRUD, 주소 데이터 위도/경도 변환 API, 장바구니(생성, 메뉴 추가, 전체/선택 삭제, 조회)기능,
      주문 및 결제 로직 구현(I'mport), 검색창 기능 구현(타입별 검색, 가게명/메뉴명/지역명 검색, 검색 결과 정렬)

### ⚙개발 환경
- Language : `HTML5` `CSS` `JavaScript(ES6)` `Java SE17` `React` `MUI` `Node.js(Socket)`
- IDE : `IntelliJ IDEA` `VS Code`
- FrameWork : `Spring-Boot (3.X)`
- ORM : `JPA` `QueryDSL`
- Server : `Putty` `NCP`
- DataBase : `NCloud DB` `MY SQL` `Object` `Storage`
- CI/CD : `Jenkins` `Docker`
- 협업툴 : `GitHub` `Jira`

## 📌주요기능
* 로그인/회원가입
  * DB값 검증
  * ID 찾기
  * 로그인 시 토큰 생성(JWT)
* 메인페이지
  * 사용자 화면
* 지도
  * 지도API
* 마이페이지
  * 회원 관리
* 사업자페이지
  * 사업자 관리
* 가게
* 리뷰
  * 리뷰(가게별 조회, 등록, 삭제)
* 포장 주문
* 결제
* 장바구니
* 줄 서기
