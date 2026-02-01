package com.booking.controller;


import com.booking.dto.request.ScreenDto;
import com.booking.dto.response.ResponseScreenDto;
import com.booking.service.ScreenService;
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
@RequestMapping("/api/v1/screens")
@Validated
public class ScreensController {

    @Autowired
    private ScreenService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseScreenDto> addScreens(@Valid @RequestBody ScreenDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addScreens(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseScreenDto> getScreen(@RequestParam(required = false) UUID id) {
        return ResponseEntity.ok(service.getScreens(id));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseScreenDto> updateScreens(@Valid @RequestBody ScreenDto dto) {
        return ResponseEntity.ok(service.updateScreens(dto));
    }

    @DeleteMapping("/details")
    public ResponseEntity<String> deleteScreen(@RequestParam(required = true) UUID id) {
        service.deleteScreens(id);
        return ResponseEntity.ok("screens deleted successfully");
    }
}
