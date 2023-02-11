package com.orderpicker.user.application.impl;

import com.orderpicker.user.application.exception.UserNotFoundException;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.domain.repository.UserRepository;
import com.orderpicker.user.infrastructure.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class UserServiceImp implements UserService {

    private final @NonNull UserRepository userRepository;

    protected void findByDni(String dni){
        Optional<User> userFound = this.userRepository.findByDni(dni);
        if(userFound.isPresent()){
            throw new UserNotFoundException("The user with dni %s already exist".formatted(dni));
        }
    }

    protected void findByEmail(String email){
        Optional<User> userFound = this.userRepository.findByEmail(email);
        if(userFound.isPresent()){
            throw new UserNotFoundException("The user with email %s already exist".formatted(email));
        }
    }
}
