package com.booking.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;


public record UserDto(
        UUID uuid,
        @NotBlank(message = "email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "city is required")
        String city
) {
}
