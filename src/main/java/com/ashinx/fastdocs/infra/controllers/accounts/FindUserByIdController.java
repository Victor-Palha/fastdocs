package com.ashinx.fastdocs.infra.controllers.accounts;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.useCases.FindUserByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindUserByIdController {
    final private FindUserByIdUseCase findUserByIdUseCase;

    public FindUserByIdController(FindUserByIdUseCase findUserByIdUseCase) {
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> execute(@PathVariable String id) {
        UserEntity user = this.findUserByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
