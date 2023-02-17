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

    public User updateUser(User user, UserDTO userDTO){
        user.setDni(userDTO.getDni());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public User showUserOrder(User user){
        return User.builder()
                .id(user.getId())
                .dni(user.getDni())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password("")
                .createdAt(user.getCreatedAt())
                .updateAt(user.getUpdateAt())
                .build();
    }
}
