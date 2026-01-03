package com.ashinx.fastdocs.infra.persistence.gateways;

import com.ashinx.fastdocs.domain.accounts.mappers.UserMapper;
import com.ashinx.fastdocs.domain.documents.gateway.UserGateway;
import com.ashinx.fastdocs.infra.persistence.jpa.SpringDataUserJpaRepository;
import com.ashinx.fastdocs.infra.persistence.models.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ImpUserGateway implements UserGateway {

    private final SpringDataUserJpaRepository jpaRepo;

    public ImpUserGateway(SpringDataUserJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    public Optional<UUID> findUserCompanyByUserId(UUID userId) {
        return jpaRepo
                .findById(userId)
                .map(UserModel::getCompanyId);
    }
}
