package com.booking.mappers;

import com.booking.dto.request.ShowTimeDto;
import com.booking.dto.response.ResponseShowTimeDto;
import com.booking.entity.Showtime;


public class ShowTimeMapper {

    public static ResponseShowTimeDto mapShowTimeDto(Showtime showTime) {
        return new ResponseShowTimeDto(
                showTime.getId(),
                showTime.getDateTime(),
                showTime.getPrice(),
                ScreenMapper.mapScreenToDto(showTime.getScreen()),
                MovieMapper.mapMovieToDto(showTime.getMovie())
               // showTime.getBooking() == null ? null :
                //showTime.getBooking().stream().map(BookingMapper::mapToDto).toList()
        );
    }

    public static Showtime mapShowTimeDtoEntity(ShowTimeDto dto) {
        return Showtime.builder()
                .dateTime(dto.dateTime())
                .price(dto.price())
                //.screen(ScreenMapper.mapScreenDToaEntity(dto.screenDto()))
                //.movie(MovieMapper.mapMovieDtoEntity(dto.movieDto()))
                //.booking(dto.booking().stream().map(BookingMapper::mapToDtoEntity).toList())
                .build();
    }
}
