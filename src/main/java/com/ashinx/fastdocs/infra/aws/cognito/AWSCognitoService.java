package com.ashinx.fastdocs.infra.aws.cognito;

import com.ashinx.fastdocs.domain.accounts.auth.OAuthService;
import com.ashinx.fastdocs.domain.accounts.auth.dtos.OAuthTokenResponse;
import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

@Component
public class AWSCognitoService implements OAuthService {
    private final CognitoIdentityProviderClient cognitoClient;
    private final WebClient webClient;

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.domain}")
    private String cognitoDomain;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.client-secret}")
    private String clientSecret;

    @Value("${aws.cognito.redirect-uri}")
    private String redirectUri;

    public AWSCognitoService(CognitoIdentityProviderClient cognitoClient,  WebClient webClient) {
        this.cognitoClient = cognitoClient;
        this.webClient = webClient;
    }

    public String createUser(UserEntity data) {
        try {
            AdminCreateUserRequest cognitoRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(data.getUsername())
                    .userAttributes(
                            AttributeType.builder()
                                    .name("email")
                                    .value(data.getEmail())
                                    .build(),
                            AttributeType.builder()
                                    .name("email_verified")
                                    .value("true")
                                    .build(),
                            AttributeType.builder()
                                    .name("name")
                                    .value(data.getUsername())
                                    .build(),
                            AttributeType.builder()
                                    .name("custom:tenent_id")
                                    .value(data.getCompanyId().toString())
                                    .build()
                    )
                    .temporaryPassword(data.getPassword())
                    .messageAction(MessageActionType.SUPPRESS)
                    .desiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(cognitoRequest);

            return response.user().attributes().stream()
                    .filter(attr -> "sub".equals(attr.name()))
                    .map(AttributeType::value)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("sub não retornado pelo Cognito"));
        } catch (UsernameExistsException e) {
            throw new RuntimeException("Usuário já existe no Cognito", e);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Erro ao criar usuário no Cognito: " + e.getMessage(), e);
        }
    }

    public OAuthTokenResponse callback(String code) {
        return webClient.post()
                .uri(cognitoDomain + "/oauth2/token")
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
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    public void addUserToGroup(String username, String group) {
        try {
            AdminAddUserToGroupRequest request = AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .groupName(group)
                    .build();

            cognitoClient.adminAddUserToGroup(request);

        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Usuário ou grupo não encontrado", e);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Erro ao adicionar usuário ao grupo: " + e.getMessage(), e);
        }
    }
}
