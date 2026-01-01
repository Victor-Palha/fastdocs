package com.ashinx.fastdocs.modules.accounts.services;

import com.ashinx.fastdocs.modules.accounts.controllers.dtos.CognitoTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class CognitoOAuthService {
    private final WebClient webClient;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.client-secret}")
    private String clientSecret;

    @Value("${aws.cognito.redirect-uri}")
    private String redirectUri;

    public CognitoOAuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public CognitoTokenResponse exchangeCodeForToken(String code) {
        return webClient.post()
                .uri("https://us-east-2ioy2hbtls.auth.us-east-2.amazoncognito.com/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        BodyInserters.fromFormData("grant_type", "authorization_code")
                                .with("code", code)
                                .with("redirect_uri", redirectUri)
                                .with("client_id", clientId)
                                .with("client_secret", clientSecret)
                )
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Cognito error: " + body)))
                )
                .bodyToMono(CognitoTokenResponse.class)
                .block();
    }
}
