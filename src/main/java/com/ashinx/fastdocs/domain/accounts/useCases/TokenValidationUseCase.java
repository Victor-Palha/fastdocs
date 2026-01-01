package com.ashinx.fastdocs.domain.accounts.useCases;

import com.ashinx.fastdocs.domain.accounts.auth.OAuthService;
import com.ashinx.fastdocs.domain.accounts.auth.dtos.OAuthTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class TokenValidationUseCase {
    final private OAuthService oAuthService;
    public TokenValidationUseCase(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    public OAuthTokenResponse execute(String code) {
        return this.oAuthService.callback(code);
    }
}
