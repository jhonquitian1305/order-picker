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
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .role(userDTO.getRole())
                .build();
    }

    public UserDTO mapUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .dni(user.getDni())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    public User updateUser(User user, UserDTO userDTO){
        user.setDni(userDTO.getDni());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());

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
