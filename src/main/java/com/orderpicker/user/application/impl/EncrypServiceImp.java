package com.orderpicker.user.application.impl;

import com.orderpicker.user.infrastructure.service.EncryptService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncrypServiceImp implements EncryptService {
    @Override
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
