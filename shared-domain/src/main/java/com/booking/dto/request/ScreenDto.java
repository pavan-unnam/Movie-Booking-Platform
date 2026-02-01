package com.booking.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record ScreenDto(
        UUID theatreId,
        UUID uuid,
        @NotBlank(message = "name is) required")
        String name,
        @NotNull(message = "capacity is required")
        Integer capacity,
        @NotBlank(message = "layout is required")
        String layout
        //TheatreDto theatre,
        //List<ShowTimeDto> showtime
) {
}
