package com.booking.dto.request;


import com.booking.enums.TheatreStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TheatreDto(
        UUID uuid,
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "city is required")
        String city,
        @NotNull(message = "Status is required")
        TheatreStatus status,
        LocalDateTime createdAt,
        List<ScreenDto> screens
) {}
