package com.booking.mappers;

import com.booking.dto.request.UserDto;
import com.booking.dto.response.ResponseUserDto;
import com.booking.entity.User;


public class UserMapping {

    public static ResponseUserDto mapUserToDto(User user) {
        return new ResponseUserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCity()
                //user.getBookings() == null ? nullz :
                //user.getBookings().stream().map(BookingMapper::mapToDto).toList()
        );
    }


    public static User mapUserToDtoEntity(UserDto user) {
        return User.builder()
                .name(user.name())
                .city(user.city())
                .email(user.email())
                //.bookings(user.booking().stream().map(BookingMapper::mapToDtoEntity).toList())
                .build();
    }

}
