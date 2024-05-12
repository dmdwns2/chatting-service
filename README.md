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

## [swagger(local)](http://localhost:8080/swagger-ui/index.html)

## ![How to Login](https://github.com/dmdwns2/chatting-service/issues/75)

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

--- 
