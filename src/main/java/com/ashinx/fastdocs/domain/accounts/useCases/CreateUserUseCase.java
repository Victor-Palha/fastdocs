package com.ashinx.fastdocs.domain.accounts.useCases;

import com.ashinx.fastdocs.domain.accounts.auth.OAuthService;
import com.ashinx.fastdocs.domain.accounts.useCases.dtos.CreateUserRequest;
import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final OAuthService oAuthService;

    public CreateUserUseCase(UserRepository userRepository, OAuthService oAuthService) {
        this.userRepository = userRepository;
        this.oAuthService = oAuthService;
    }

    public UserEntity execute(CreateUserRequest data) {
        UserEntity userToBeCreated = UserEntity.create(
                data.username(),
                data.email()
        );
        String tempPassword = this.generateTemporaryPassword();
        userToBeCreated.setPassword(tempPassword);

        String oAuthUserId = this.oAuthService.createUser(userToBeCreated);
        userToBeCreated.setId(UUID.fromString(oAuthUserId));
        this.oAuthService.addUserToGroup(
                userToBeCreated.getUsername(),
                userToBeCreated.getRole().toString()
        );

        return userRepository.create(userToBeCreated);
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID() + "Aa1!";
    }
}
