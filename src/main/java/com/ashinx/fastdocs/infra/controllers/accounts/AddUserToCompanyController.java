package com.ashinx.fastdocs.infra.controllers.accounts;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.useCases.AddUserToCompanyUseCase;
import com.ashinx.fastdocs.domain.accounts.useCases.dtos.AddUserRequest;
import com.ashinx.fastdocs.infra.controllers.accounts.dtos.CreateUserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AddUserToCompanyController {
    final private AddUserToCompanyUseCase addUserToCompanyUseCase;

    public AddUserToCompanyController(AddUserToCompanyUseCase addUserToCompanyUseCase) {
        this.addUserToCompanyUseCase = addUserToCompanyUseCase;
    }

    @PostMapping("/user/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> execute(
            @Valid @RequestBody CreateUserRequestDTO request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String adminUserId = jwt.getSubject();
        AddUserRequest params = new AddUserRequest(
                request.username(),
                request.email(),
                UUID.fromString(adminUserId)
        );

        UserEntity userAdded = this.addUserToCompanyUseCase.execute(params);
        return ResponseEntity.status(HttpStatus.CREATED).body(userAdded);
    }
}
