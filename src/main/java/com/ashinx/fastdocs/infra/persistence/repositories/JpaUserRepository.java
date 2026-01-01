package com.ashinx.fastdocs.infra.persistence.repositories;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.mappers.UserMapper;
import com.ashinx.fastdocs.domain.accounts.repositories.UserRepository;
import com.ashinx.fastdocs.infra.persistence.jpa.SpringDataUserJpaRepository;
import com.ashinx.fastdocs.infra.persistence.models.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserJpaRepository jpaRepo;

    public JpaUserRepository(SpringDataUserJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    public UserEntity create(UserEntity data) {
        UserModel userModel = UserMapper.toPersistence(data);
        jpaRepo.save(userModel);
        return data;
    }
}
