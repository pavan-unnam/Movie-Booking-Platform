package com.booking.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ShowTimeDto(
        UUID uuid,
        UUID screenId,
        UUID movieId,
        @NotNull(message = "dateTime is required")
        LocalDateTime dateTime,
        @NotNull(message = "price is required")
        BigDecimal price){
}
