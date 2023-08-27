
run:
	./gradlew bootRun -D spring.profiles.active=local

docker-compose-up:
	make -C ./docker docker-compose-up

docker-compose-down:
	make -C ./docker docker-compose-down
