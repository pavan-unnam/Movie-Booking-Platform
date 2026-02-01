package com.booking.dto.response;

import com.booking.enums.BookingStatus;

import java.util.UUID;

public record ResponseBookingDto(
        UUID uuid,
        String seatId,
        BookingStatus status,
        ResponseShowTimeDto showTime,
        ResponseUserDto user
) {
}
