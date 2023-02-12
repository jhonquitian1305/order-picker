package com.orderpicker.user.infrastructure.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotNull(message = "The dni field couldn't be null")
    @NotEmpty(message = "The dni field couldn't be empty")
    private String dni;

    @NotNull(message = "The full name field couldn't be null")
    @NotEmpty(message = "The full name field couldn't be empty")
    private String fullName;

    @NotNull(message = "The email field couldn't be null")
    @NotEmpty(message = "The email field couldn't be empty")
    private String email;

    @NotNull(message = "The password field couldn't be null")
    @NotEmpty(message = "The password field couldn't be empty")
    private String password;
}

