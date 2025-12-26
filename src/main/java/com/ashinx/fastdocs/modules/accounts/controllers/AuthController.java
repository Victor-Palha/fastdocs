package com.ashinx.fastdocs.modules.accounts.controllers;

import com.ashinx.fastdocs.modules.accounts.dto.CognitoTokenResponse;
import com.ashinx.fastdocs.modules.accounts.services.CognitoOAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AuthController {
    private final CognitoOAuthService cognitoOAuthService;

    public AuthController(CognitoOAuthService cognitoOAuthService) {
        this.cognitoOAuthService = cognitoOAuthService;
    }

    @GetMapping("/public/auth")
    public ResponseEntity<CognitoTokenResponse> execute(@RequestParam(name = "code") String code) {
        CognitoTokenResponse tokens = cognitoOAuthService.exchangeCodeForToken(code);
        return ResponseEntity.ok(tokens);
    }
}
