package com.example.repository;

import com.example.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJPAEntity, Long> {
    Optional<UserJPAEntity> findByName(String name);

    Optional<UserJPAEntity> findByNickname(String nickName);
}