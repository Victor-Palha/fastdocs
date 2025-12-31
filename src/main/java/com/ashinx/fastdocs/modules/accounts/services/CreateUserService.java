package com.ashinx.fastdocs.modules.accounts.services;

import com.ashinx.fastdocs.modules.accounts.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private UserRepository userRepository;

    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() {

    }
}