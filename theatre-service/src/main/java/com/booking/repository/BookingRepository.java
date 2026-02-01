package com.booking.repository;

import com.booking.entity.Booking;
import com.booking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findBySeatIdAndStatusAndShowtimeId(String id, BookingStatus status, UUID uuid);
}
