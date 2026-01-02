package com.ashinx.fastdocs.domain.accounts.useCases;

import com.ashinx.fastdocs.domain.accounts.entities.UserEntity;
import com.ashinx.fastdocs.domain.accounts.exceptions.UserNotFoundException;
import com.ashinx.fastdocs.domain.accounts.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindUserByIdUseCase {
    final private UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity execute(String id){
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(id));
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }
        return user.get();
    }
}
