package com.orderpicker.user.infrastructure.service;

import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import com.orderpicker.user.infrastructure.response.UserResponse;

public interface UserService {
    User save(UserDTO userDTO);
    User getById(Long id);
    UserResponse findAll(int numberPage, int pageSize, String sortBy, String sortDir);
    User getByDni(String dni);
    User getByEmail(String email);
    User updateOne(Long id, UserDTO userDTO);
}
