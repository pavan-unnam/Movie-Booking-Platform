package com.booking.controller;


import com.booking.dto.request.ShowTimeDto;
import com.booking.dto.response.ResponseShowTimeDto;
import com.booking.service.ShowTimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/v1/showtimes")
@Validated
public class ShowTimesController {

    @Autowired
    private ShowTimeService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseShowTimeDto> addShowTimes(@Valid @RequestBody ShowTimeDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addShowTimes(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseShowTimeDto> getShowTimes(@RequestParam(required = false) UUID id) {

        return ResponseEntity.ok(service.getShowTimes(id));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseShowTimeDto> updateShowTimes(@Valid @RequestBody ShowTimeDto dto) {
        return ResponseEntity.ok(service.updateShowTimes(dto));
    }

    @DeleteMapping("/details")
    public ResponseEntity<String> deleteShowTimes(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) UUID id) {
        service.deleteShowTimes(id);
        return ResponseEntity.ok("showtimes deleted successfully");
    }
}
