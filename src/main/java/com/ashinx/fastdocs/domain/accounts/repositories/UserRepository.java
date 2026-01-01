package com.ashinx.fastdocs.domain.accounts.repositories;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;

public interface UserRepository {
    UserEntity create(UserEntity user);
}
