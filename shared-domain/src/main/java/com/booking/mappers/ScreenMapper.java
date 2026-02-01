package com.booking.mappers;

import com.booking.dto.request.ScreenDto;
import com.booking.dto.response.ResponseScreenDto;
import com.booking.entity.Screen;


public class   ScreenMapper {
    public static ResponseScreenDto mapScreenToDto(Screen screen) {
        return new ResponseScreenDto(
                screen.getId(),
                screen.getName(),
                screen.getCapacity(),
                screen.getLayout(),
                TheatreMapper.mapToDto(screen.getTheatre())
               // screen.getShowTimes() == null ? null :
                //screen.getShowTimes().stream().map(ShowTimeMapper::mapShowTimeDto).toList()
        );
    }

    public static Screen mapScreenDToaEntity(ScreenDto dto) {
        return Screen.builder()
                .name(dto.name())
                .capacity(dto.capacity())
                .layout(dto.layout())
                //.theatre(TheatreMapper.mapDtoEntity(dto.theatre()))
                //.showTimes(dto.showtime().stream().map(ShowTimeMapper::mapShowTimeDtoEntity).toList())
                .build();

    }
}
