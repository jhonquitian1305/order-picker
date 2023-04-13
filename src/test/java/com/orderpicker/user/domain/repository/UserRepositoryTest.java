package com.orderpicker.user.domain.repository;

import com.orderpicker.rol.Role;
import com.orderpicker.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("Test UserRepository, Test to get a user by email")
    @Test
    void getOneByEmail(){
        this.userRepository.save(user);

        Optional<User> userFound = this.userRepository.findByEmail(user.getEmail());

        assertNotNull(userFound);
        assertEquals(user.getEmail(), userFound.get().getEmail());
    }

    @DisplayName("Test UserRepository, Test when to search a user that not exists")
    @Test
    void userNotFound(){
        this.userRepository.save(user);

        Optional<User> userSearchById = this.userRepository.findById(5L);
        Optional<User> userSearchByDni = this.userRepository.findByDni("34544535443");
        Optional<User> userSearchByEmail = this.userRepository.findByEmail("jhondoe@mail.com");

        assertTrue(userSearchById.isEmpty());
        assertTrue(userSearchByDni.isEmpty());
        assertTrue(userSearchByEmail.isEmpty());
    }

    @DisplayName("Test UserRepository, Test to update a user")
    @Test
    void updateOne(){
        this.userRepository.save(user);

        user.setEmail("jhondoe@mail.com");
        this.userRepository.save(user);
        Optional<User> userUpdated = this.userRepository.findByEmail("jhondoe@mail.com");

        assertEquals("jhondoe@mail.com", userUpdated.get().getEmail());
    }

    @DisplayName("Test UserRepository, Test to delete a user")
    @Test
    void deleteOne(){
        this.userRepository.save(user);

        this.userRepository.deleteById(user.getId());
        Optional<User> userDeleted = this.userRepository.findById(user.getId());

        assertTrue(userDeleted.isEmpty());
    }
}
