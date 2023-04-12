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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @DisplayName("Test UserRepository, Test to save a user")
    @Test
    void createOne(){
        User userSaved = this.userRepository.save(user);

        assertNotNull(userSaved);
        assertThat(userSaved.getId()).isPositive();
    }

    @DisplayName("Test UserRepository, Test to get a user by id")
    @Test
    void getOneById(){
        this.userRepository.save(user);

        Optional<User> userFound = this.userRepository.findById(user.getId());

        assertNotNull(userFound);
    }

    @DisplayName("Test UserRepository, Test to get all users")
    @Test
    void getAll(){
        this.userRepository.save(user);

        List<User> listUsers = this.userRepository.findAll();

        assertNotNull(listUsers);
        assertEquals(1, listUsers.size());
    }

    @DisplayName("Test UserRepository, Test to get a user by dni")
    @Test
    void getOneByDni(){
        this.userRepository.save(user);

        Optional<User> userFound = this.userRepository.findByDni(user.getDni());

        assertNotNull(userFound);
        assertEquals(user.getDni(), userFound.get().getDni());
    }
}
