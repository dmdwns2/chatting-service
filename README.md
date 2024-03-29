# chatting-service
첫 개인 토이프로젝트 채팅서비스

## How to Run

[Start Server에서 ./gradlew: /bin/sh^M: bad interpreter 에러 발생 시](https://github.com/dmdwns2/chatting-service/issues/28)

```shell
# Start mysql
$ make -C persistence docker-compose-up

# Start server
$ ./gradlew :presentation:bootRun

# Stop mysql
$ make -C persistence docker-compose-down
```

## swagger
http://localhost:8080/swagger-ui/index.html

## How to Login
1. 회원가입 후 로그인을 진행하면 response로 세션 id 값이 나옵니다. 이것을 복사해줍니다.
   
   If you log in after signing up, you will get a session id value as response. Copy this.
<img width="577" alt="스크린샷 2024-03-07 오후 8 53 07" src="https://github.com/dmdwns2/chatting-service/assets/105894868/7febbb43-afe4-43fa-b386-8911504d4ca4">

2. 맨 위로 올라가면 오른쪽에 Authorize 버튼이 있습니다. 눌러줍니다.
   
   If you go up to the top, there is an Authorize button on the right, press it.
<img width="1408" alt="스크린샷 2024-03-07 오후 8 53 19" src="https://github.com/dmdwns2/chatting-service/assets/105894868/a6913278-ac8e-49a0-a4fb-83315c7129b3">

3. Value에 1에서 복사해둔 세션 id를 붙여넣고 Authorize 버튼을 눌러줍니다.

   Paste the session id copied from 1 into Value and press the Authorize button.
<img width="648" alt="스크린샷 2024-03-07 오후 9 06 10" src="https://github.com/dmdwns2/chatting-service/assets/105894868/7b144dff-406e-4223-8b15-636b59b7dabd">


## convention

- Google Java Style Guide
- ![Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)

## To do

- [x] ![클린 아키텍처 적용](https://github.com/dmdwns2/chatting-service/issues/8)
---
- [x] ![유스케이스 다이어그램](https://github.com/dmdwns2/chatting-service/issues/2)
- [x] ![도메인/엔티티 모델 정의](https://github.com/dmdwns2/chatting-service/issues/1) 
- [x] ![API 설계 / UI 설계](https://github.com/dmdwns2/chatting-service/issues/3)
- [x] ![Schema 설계](https://github.com/dmdwns2/chatting-service/issues/7)
- [ ] 기능 구현
  - [x] 회원가입
  - [x] 로그인/로그아웃
  - [x] 채팅방 생성
  - [x] 채팅방 목록 조회
  - [x] 채팅방 입장
  - [x] 채팅방 나가기
  - [x] 메시지 갱신
  - [x] 메시지 전송
- [ ] UI
  - [x] Swagger : SpringDoc 라이브러리 적용
- [ ] 성능 테스트
  - [ ] nGrinder 부하테스트 실행
  - [ ] 성능 개선
  - [ ] 결과
