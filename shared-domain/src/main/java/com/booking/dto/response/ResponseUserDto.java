package com.booking.dto.response;


import java.util.UUID;

public record ResponseUserDto(
        UUID uuid,
        String email,
        String name,
        String city
) {
}
