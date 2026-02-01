package com.booking.dto.response;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResponseMovieDto(
        UUID uuid,
        String title,
        String description,
        List<String> genres,
        Integer durationMinutes,
        LocalDate releaseDate,
        Double rating,
        LocalDateTime createdAt) {
}
