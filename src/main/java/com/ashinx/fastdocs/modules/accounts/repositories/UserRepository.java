package com.ashinx.fastdocs.modules.accounts.repositories;

import com.ashinx.fastdocs.modules.accounts.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
