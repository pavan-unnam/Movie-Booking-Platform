package com.booking.dto.request;

import com.booking.enums.BookingStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingDto(
        UUID uuid,
        @NotBlank(message = "seatId is required")
        String seatId,
        @NotNull(message = "status is required")
        BookingStatus status,
        @NotNull(message = "showTimeDto is required")
        @Valid
        ShowTimeDto showTime,
        @NotNull(message = "userDto is required")
        @Valid
        UserDto user
) {
}
