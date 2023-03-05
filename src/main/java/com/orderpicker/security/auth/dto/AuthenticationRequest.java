package com.orderpicker.security.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull(message = "email field should not be null")
    @NotEmpty(message = "email field should not be empty")
    private String email;

    @NotNull(message = "password field should not be null")
    @NotEmpty(message = "password field should not be empty")
    private String password;
}
