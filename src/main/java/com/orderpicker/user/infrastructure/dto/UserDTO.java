package com.orderpicker.user.infrastructure.dto;

import com.orderpicker.rol.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotNull(message = "Address field couldn't be null")
    @NotEmpty(message = "Address field couldn't be empty")
    @Size(min = 5, max = 100)
    private String address;

    @NotNull(message = "Phone field couldn't be null")
    @NotEmpty(message = "Phone field couldn't be empty")
    @Size(min = 7, max = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "role field must be EMPLOYEE or USER")
    private Role role;
}

