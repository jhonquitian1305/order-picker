package com.orderpicker.user.application.usecase;

import com.orderpicker.rol.Role;
import com.orderpicker.user.application.mapper.MapperUser;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.domain.repository.UserRepository;
import com.orderpicker.user.infrastructure.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

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
}