package com.orderpicker.user.domain.repository;

import com.orderpicker.rol.Role;
import com.orderpicker.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles(profiles = "test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void begin(){
        user = User.builder()
                .dni("123456789")
                .fullName("John Doe")
                .email("johndoe@mail.com")
                .password("123456789")
                .address("Calle 50")
                .phone("273428347782")
                .role(Role.USER)
                .build();
    }
}
