package com.ashinx.fastdocs.domain.accounts.useCases;

import com.ashinx.fastdocs.domain.accounts.dtos.CreateUserRequest;
import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.UUID;

@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    public CreateUserUseCase(UserRepository userRepository, CognitoIdentityProviderClient cognitoClient) {
        this.userRepository = userRepository;
        this.cognitoClient = cognitoClient;
    }

    public UserEntity execute(CreateUserRequest data) {
        UserEntity userToBeCreated = UserEntity.create(
                data.username(),
                data.email()
        );

        String cognitoUserId = this.createCognitoUser(userToBeCreated);
        UserEntity userCreated = UserEntity.update(
                UUID.fromString(cognitoUserId),
                userToBeCreated.getUsername(),
                userToBeCreated.getEmail(),
                userToBeCreated.getCompanyId(),
                userToBeCreated.getRole()
        );

        return userRepository.create(userCreated);
    }

    private String createCognitoUser(UserEntity data) {
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
                    .temporaryPassword(generateTemporaryPassword())
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

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString() + "Aa1!";
    }
}
