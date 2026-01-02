package com.ashinx.fastdocs.domain.accounts.repositories;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    UserEntity create(UserEntity user);
    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findByEmail(String email);
}
