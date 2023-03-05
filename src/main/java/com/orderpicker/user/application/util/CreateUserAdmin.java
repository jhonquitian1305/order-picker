package com.orderpicker.user.application.util;

import com.orderpicker.rol.Role;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class CreateUserAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User userAdmin = User.builder()
                .dni("12345678")
                .fullName("admin")
                .email("admin")
                .password(passwordEncoder.encode("admin"))
                .address("admin street")
                .phone("12345678")
                .role(Role.ADMIN)
                .build();
        this.saveAdmin(userAdmin);
    }

    protected void saveAdmin(User user){
        Optional<User> userFound = this.userRepository.findByEmail("admin");
        if(userFound.isEmpty()){
            this.userRepository.save(user);
        }
    }
}
