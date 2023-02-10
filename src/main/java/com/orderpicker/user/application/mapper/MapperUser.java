package com.orderpicker.user.application.mapper;

import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {
    public User mapUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .dni(userDTO.getDni())
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public UserDTO mapUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .dni(user.getDni())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }
}
