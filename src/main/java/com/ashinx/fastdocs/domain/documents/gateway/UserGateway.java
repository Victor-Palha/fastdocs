package com.ashinx.fastdocs.domain.documents.gateway;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    public Optional<UUID> findUserCompanyByUserId(UUID userId);
}
