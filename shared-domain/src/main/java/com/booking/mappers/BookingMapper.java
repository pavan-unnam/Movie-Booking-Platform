package com.booking.mappers;

import com.booking.dto.request.BookingDto;
import com.booking.dto.response.ResponseBookingDto;
import com.booking.entity.Booking;

public class BookingMapper {

    public static ResponseBookingDto mapToDto(Booking booking) {
        return new ResponseBookingDto(
                booking.getId(),
                booking.getSeatId(),
                booking.getStatus(),
                ShowTimeMapper.mapShowTimeDto(booking.getShowtime()),
                UserMapping.mapUserToDto(booking.getUser())
        );
    }


    public static Booking mapToDtoEntity(BookingDto dto) {
        return Booking.builder().seatId(dto.seatId())
                        .status(dto.status())
                .showtime(ShowTimeMapper.mapShowTimeDtoEntity(dto.showTime()))
                .user(UserMapping.mapUserToDtoEntity(dto.user()))
                .build();
    }
}
