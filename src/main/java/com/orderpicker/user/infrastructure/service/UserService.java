package com.orderpicker.user.infrastructure.service;

import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.infrastructure.dto.UserDTO;

public interface UserService {
    User save(UserDTO userDTO);
    User getById(Long id);
}
