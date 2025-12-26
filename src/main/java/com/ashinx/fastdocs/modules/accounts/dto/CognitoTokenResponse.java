package com.ashinx.fastdocs.modules.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CognitoTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("expires_in") Integer expiresIn,
        @JsonProperty("token_type") String tokenType
) { }
