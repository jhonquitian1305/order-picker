package com.orderpicker.user.application.usecase;

import com.orderpicker.exception.BadRequestException;
import com.orderpicker.exception.NotFoundException;
import com.orderpicker.rol.Role;
import com.orderpicker.user.application.mapper.MapperUser;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.domain.repository.UserRepository;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    @Mock
    private MapperUser mapperUser;

    @Mock
    private EncryptServiceImp encryptService;

    private User user;

    private UserDTO userDTO;

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

        userDTO = UserDTO.builder()
                .dni("123456789")
                .fullName("John Doe")
                .email("johndoe@mail.com")
                .password("123456789")
                .address("Calle 50")
                .phone("273428347782")
                .role(Role.USER)
                .build();
    }

    @DisplayName("Test UserService, Test to save a user")
    @Test
    void createOne(){
        given(this.userRepository.findByDni(this.userDTO.getDni())).willReturn(Optional.empty());
        given(this.userRepository.findByEmail(this.userDTO.getEmail())).willReturn(Optional.empty());
        given(this.encryptService.encryptPassword(anyString())).willReturn("hash password");
        given(this.mapperUser.mapUser(any(UserDTO.class))).willReturn(user);
        given(this.userRepository.save(any(User.class))).willReturn(user);

        User userSaved = this.userService.save(this.userDTO);

        assertNotNull(userSaved);
        assertNotEquals(Role.ADMIN, userSaved.getRole());
    }

    @DisplayName("Test UserService, Test to save a user when the dni exists")
    @Test
    void failSaveWhenDniExists(){
        given(this.userRepository.findByDni(this.userDTO.getDni())).willReturn(Optional.of(user));

        BadRequestException userFoundDni = assertThrows(BadRequestException.class, () -> {
            this.userService.save(this.userDTO);
        });

        verify(this.userRepository, never()).save(any(User.class));
        assertEquals("The user with dni %s already exists".formatted(this.userDTO.getDni()), userFoundDni.getMessage());
    }

    @DisplayName("Test UserService, Test to a save a user when the email exists")
    @Test
    void failSaveWhenEmailExists(){
        given(this.userRepository.findByEmail(this.userDTO.getEmail())).willReturn(Optional.of(user));

        BadRequestException userFoundEmail = assertThrows(BadRequestException.class, () -> {
           this.userService.save(this.userDTO);
        });

        verify(this.userRepository, never()).save(any(User.class));
        assertEquals("The user with email %s already exists".formatted(this.userDTO.getEmail()), userFoundEmail.getMessage());
    }

    @DisplayName("Test UserService, Test to get a user by id")
    @Test
    void getOneById(){
        given(this.userRepository.findById(this.userDTO.getId())).willReturn(Optional.of(user));

        User userFoundById = this.userService.getById(this.userDTO.getId());

        assertNotNull(userFoundById);
    }

    @DisplayName("Test UserService, test to get a user by id when doesn't exist")
    @Test
    void failGetOneById(){
        given(this.userRepository.findById(anyLong())).willReturn(Optional.empty());

        NotFoundException userNotFoundById = assertThrows(NotFoundException.class, () -> {
            this.userService.getById(1L);
        });

        assertEquals("User with id %s doesn't exist".formatted(1L), userNotFoundById.getMessage());
    }

    @DisplayName("Test UserService, Test to get a user by dni")
    @Test
    void getOneByDni(){
        given(this.userRepository.findByDni(this.userDTO.getDni())).willReturn(Optional.of(user));

        User userFoundByDni = this.userService.getByDni(this.userDTO.getDni());

        assertNotNull(userFoundByDni);
    }

    @DisplayName("Test UserService, test to get a user by dni when doesn't exist")
    @Test
    void failGetOneByDni(){
        given(this.userRepository.findByDni(anyString())).willReturn(Optional.empty());

        NotFoundException userNotFoundByDni = assertThrows(NotFoundException.class, () -> {
            this.userService.getByDni("255971121");
        });

        assertEquals("User with dni %s doesn't exist".formatted("255971121"), userNotFoundByDni.getMessage());
    }

    @DisplayName("Test UserService, Test to get a user by email")
    @Test
    void getOneByEmail(){
        given(this.userRepository.findByEmail(this.userDTO.getEmail())).willReturn(Optional.of(user));

        User userFoundByEmail = this.userService.getByEmail(this.userDTO.getEmail());

        assertNotNull(userFoundByEmail);
        assertEquals(this.userDTO.getEmail(), userFoundByEmail.getEmail());
    }
}
