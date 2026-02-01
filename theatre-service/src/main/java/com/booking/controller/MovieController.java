package com.booking.controller;


import com.booking.dto.request.MovieDto;
import com.booking.dto.response.ResponseMovieDto;
import com.booking.service.MovieService;
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
@RequestMapping("/api/v1/movies")
@Validated
public class MovieController {

    @Autowired
    private MovieService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseMovieDto> addMovies(@Valid @RequestBody MovieDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addMovies(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseMovieDto> getMovies(@RequestParam(required = false) String title,
                                                         @RequestParam(required = false) UUID id) {

        return ResponseEntity.ok(service.getMovies(id, title));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMovieDto> updateMovies(@Valid @RequestBody MovieDto dto) {
        return ResponseEntity.ok(service.updateMovies(dto));
    }

    @DeleteMapping("/details")
    public ResponseEntity<String> deleteMovies(@RequestParam(required = false) String title,
                                                @RequestParam(required = false) UUID id) {
        service.deleteMovies(id, title);
        return ResponseEntity.ok("Theatre deleted successfully");
    }
}
