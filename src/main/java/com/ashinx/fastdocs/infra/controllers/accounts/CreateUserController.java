package com.ashinx.fastdocs.infra.controllers.accounts;

import com.ashinx.fastdocs.domain.accounts.dtos.CreateUserRequest;
import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.useCases.CreateUserUseCase;
import com.ashinx.fastdocs.infra.controllers.accounts.dtos.CreateUserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController {
    private final CreateUserUseCase createUserUseCase;

    public CreateUserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/public/user/create")
    public ResponseEntity<UserEntity> execute(@Valid @RequestBody CreateUserRequestDTO body) {
        CreateUserRequest params = new CreateUserRequest(
                body.username(),
                body.email()
        );
        UserEntity user = this.createUserUseCase.execute(params);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
