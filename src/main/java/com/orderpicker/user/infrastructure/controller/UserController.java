package com.orderpicker.user.infrastructure.controller;


import com.orderpicker.user.application.exception.UserBadRequestException;
import com.orderpicker.user.application.mapper.MapperUser;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import com.orderpicker.user.infrastructure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.orderpicker.user.infrastructure.constants.UserEndpointsConstants.ENDPOINT_USERS;
import static com.orderpicker.user.infrastructure.constants.UserEndpointsConstants.ENDPOINT_USER_ID;

@RestController
@RequestMapping(ENDPOINT_USERS)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final MapperUser mapperUser;

    @PostMapping()
    ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new UserBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.save(userDTO)), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_USER_ID)
    ResponseEntity<UserDTO> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.mapperUser.mapUserDTO(this.userService.getById(id)), HttpStatus.OK);
    }
}
