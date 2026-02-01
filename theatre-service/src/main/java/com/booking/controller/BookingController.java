package com.booking.controller;

import com.booking.dto.request.BookingDto;
import com.booking.dto.response.ResponseBookingDto;
import com.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @Autowired
    private BookingService service;



    @PostMapping("/create")
    public ResponseEntity<ResponseBookingDto> createBooking(@Valid @RequestBody BookingDto dto) {
        return ResponseEntity.ok(service.createBooking(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseBookingDto> getBookingDetails(@RequestParam(required = true) UUID uuid) {
        return ResponseEntity.ok(service.getBookingDetails(uuid));
    }

    @PutMapping("/cancel")
    public ResponseEntity<ResponseBookingDto> cancelBooking(@RequestParam(required = true) UUID uuid) {
        return ResponseEntity.ok(service.cancelBooking(uuid));
    }

    @DeleteMapping("/details")
    public ResponseEntity<String> deleteBookingDetails(@RequestParam(required = true) UUID uuid) {
        service.deleteBookingDetails(uuid);
        return ResponseEntity.ok("Booking details deleted successfully");
    }

}

