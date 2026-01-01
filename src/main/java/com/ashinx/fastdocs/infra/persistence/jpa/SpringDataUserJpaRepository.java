package com.ashinx.fastdocs.infra.persistence.jpa;

import com.ashinx.fastdocs.infra.persistence.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataUserJpaRepository extends JpaRepository<UserModel, UUID> {}
