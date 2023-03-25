package com.orderpicker.security.auth.controller;

import com.orderpicker.security.auth.dto.AuthenticationRequest;
import com.orderpicker.security.auth.response.AuthenticationResponse;
import com.orderpicker.security.auth.service.AuthenticationService;
import com.orderpicker.user.application.exception.UserBadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/order-picker/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            throw new UserBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.authenticationService.authenticate(request), HttpStatus.OK);
    }
}
