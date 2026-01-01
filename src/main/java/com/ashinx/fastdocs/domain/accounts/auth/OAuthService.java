package com.ashinx.fastdocs.domain.accounts.auth;

import com.ashinx.fastdocs.domain.accounts.auth.dtos.OAuthTokenResponse;
import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;

public interface OAuthService {
    String createUser(UserEntity data);

    OAuthTokenResponse callback(String code);
}
