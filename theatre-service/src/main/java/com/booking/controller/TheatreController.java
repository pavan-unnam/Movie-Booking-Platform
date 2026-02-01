package com.booking.controller;

import com.booking.dto.request.TheatreDto;
import com.booking.dto.response.ResponseTheatreDto;
import com.booking.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/theatres")
@Validated
public class TheatreController {

    @Autowired
    private TheatreService service;

    @PostMapping("/onboard")
    public ResponseEntity<ResponseTheatreDto> onboardTheatre(@Valid @RequestBody TheatreDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.onboardTheatre(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseTheatreDto> getTheatre(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) UUID id) {

        return ResponseEntity.ok(service.getTheatre(id, name));
    }

    @PutMapping("/onboard")
    public ResponseEntity<ResponseTheatreDto> updateTheatre(@Valid @RequestBody TheatreDto dto) {
        return ResponseEntity.ok(service.updateTheatre(dto));
    }

    @DeleteMapping("/details")
    public ResponseEntity<String> deleteTheatre(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) UUID id) {
        service.deleteTheatre(id, name);
        return ResponseEntity.ok("Theatre deleted successfully");
    }
}

