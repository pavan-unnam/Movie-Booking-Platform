package com.booking.dto.response;

import java.util.UUID;

public record ResponseScreenDto(
        UUID uuid,
        String name,
        Integer capacity,
        String layout,
        ResponseTheatreDto theatre
) {}
