package com.booking.dto.response;


import com.booking.enums.TheatreStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseTheatreDto(
        UUID uuid,
        String name,
        String city,
        TheatreStatus status,
        LocalDateTime createdAt
) {}
