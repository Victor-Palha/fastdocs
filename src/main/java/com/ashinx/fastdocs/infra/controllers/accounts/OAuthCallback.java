package com.ashinx.fastdocs.infra.controllers.accounts;

import com.ashinx.fastdocs.domain.accounts.auth.dtos.OAuthTokenResponse;
import com.ashinx.fastdocs.domain.accounts.useCases.TokenValidationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthCallback {
    final TokenValidationUseCase tokenValidationUseCase;

    public OAuthCallback(TokenValidationUseCase tokenValidationUseCase) {
        this.tokenValidationUseCase = tokenValidationUseCase;
    }

    @GetMapping("/public/auth")
    public ResponseEntity<OAuthTokenResponse> execute(@RequestParam(name = "code") String code) {
        OAuthTokenResponse results = this.tokenValidationUseCase.execute(code);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
