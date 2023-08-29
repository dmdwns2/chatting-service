# chatting-service
첫 개인 토이프로젝트 채팅서비스

## How to Run

```shell
# Start mysql
$ make docker-compose-up

# Start server
$ ./gradlew bootRun

# Stop mysql
$ make docker-compose-down
```

## To do
- [x] ![유스케이스 다이어그램](https://github.com/dmdwns2/chatting-service/issues/2)
- [x] ![도메인/엔티티 모델 정의](https://github.com/dmdwns2/chatting-service/issues/1) 
- [x] ![API 설계 / UI 설계](https://github.com/dmdwns2/chatting-service/issues/3)
- [x] ![Schema 설계](https://github.com/dmdwns2/chatting-service/issues/7)
- [ ] 기능 구현
  - [ ] 회원가입
  - [ ] 로그인
  - [ ] 채팅방 목록 조회
  - [ ] 채팅방 입장
  - [ ] 채팅방 개설
  - [ ] 채팅방 나가기
  - [ ] 유저 추방
  - [ ] 메시지 갱신
  - [ ] 메시지 전송
- [ ] UI 구현
  - [ ] 로그인 화면
  - [ ] 채팅방 목록 화면
  - [ ] 채팅방 화면
- [ ] 성능 테스트
  - [ ] nGrinder
