package com.booking.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MovieDto(
        UUID uuid,
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "description is required")
        String description,
        @NotEmpty
        @Valid
        List<String> genres,
        @NotNull(message = "durationMinutes is required")
        Integer durationMinutes,
        @NotNull(message = "releaseDate is required")
        LocalDate releaseDate,
        @NotNull(message = "rating is required")
        Double rating,
        LocalDateTime createdAt,
        @NotBlank(message = "language is required")
        String language,
        @NotNull(message = "showTimes is required")
        @Valid
        List<ShowTimeDto> showTimes) {
}
