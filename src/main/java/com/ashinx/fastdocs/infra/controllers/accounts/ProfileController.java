package com.ashinx.fastdocs.infra.controllers.accounts;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
public class ProfileController {
    @GetMapping("/me")
    public Map<String, Object> execute(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims();
    }
}
