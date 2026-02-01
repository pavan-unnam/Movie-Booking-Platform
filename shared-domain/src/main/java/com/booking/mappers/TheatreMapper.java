package com.booking.mappers;

import com.booking.dto.request.TheatreDto;
import com.booking.dto.response.ResponseTheatreDto;
import com.booking.entity.Theatre;

import java.time.LocalDateTime;


public class TheatreMapper {
    public static ResponseTheatreDto mapToDto(Theatre entity) {
        if (entity == null) return null;
        return new ResponseTheatreDto(
                entity.getId(),
                entity.getName(),
                entity.getCity(),
                entity.getStatus(),
                entity.getCreatedAt()
                //entity.getScreens().stream().map(ScreenMapper::mapScreenToDto).toList()

        );
    }

    public static Theatre mapDtoEntity(TheatreDto dto) {
        return Theatre.builder()
                .city(dto.city())
                .name(dto.name())
                .status(dto.status())
                .createdAt(LocalDateTime.now())
                .screens(dto.screens().stream()
                        .map(ScreenMapper::mapScreenDToaEntity)
                        .toList())
                .build();
    }
}
