package com.ashinx.fastdocs.domain.accounts.useCases;

import com.ashinx.fastdocs.domain.accounts.auth.OAuthService;
import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.enums.UserRole;
import com.ashinx.fastdocs.domain.accounts.exceptions.UserAlreadyExistsException;
import com.ashinx.fastdocs.domain.accounts.exceptions.UserNotFoundException;
import com.ashinx.fastdocs.domain.accounts.repositories.UserRepository;
import com.ashinx.fastdocs.domain.accounts.useCases.dtos.AddUserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddUserToCompanyUseCase {
    final private UserRepository userRepository;
    final private OAuthService oAuthService;

    public AddUserToCompanyUseCase(UserRepository userRepository, OAuthService oAuthService) {
        this.userRepository = userRepository;
        this.oAuthService = oAuthService;
    }

    public UserEntity execute(AddUserRequest data) {
        Optional<UserEntity> userAlreadyExists = this.userRepository.findByEmail(data.email());
        if (userAlreadyExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Optional<UserEntity> userRequester = this.userRepository.findById(data.requesterId());
        if (userRequester.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserEntity userToBeAdded = UserEntity.create(
                data.username(),
                data.email()
        );
        String tempPassword = this.generateTemporaryPassword();
        userToBeAdded.setCompanyId(userRequester.get().getCompanyId());
        userToBeAdded.setPassword(tempPassword);
        userToBeAdded.setRole(UserRole.USER);

        String oAuthUserId = this.oAuthService.createUser(userToBeAdded);
        userToBeAdded.setId(UUID.fromString(oAuthUserId));
        this.oAuthService.addUserToGroup(
                userToBeAdded.getUsername(),
                userToBeAdded.getRole().toString()
        );

        return userRepository.create(userToBeAdded);
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID() + "Aa1!";
    }
}
