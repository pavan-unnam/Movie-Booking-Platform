package com.booking.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseShowTimeDto(
        UUID uuid,
        LocalDateTime dateTime,
        BigDecimal price,
        ResponseScreenDto screen,
        ResponseMovieDto movie) {
}
