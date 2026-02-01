package com.booking.controller;


import com.booking.dto.request.UserDto;
import com.booking.dto.response.ResponseUserDto;
import com.booking.service.UserService;
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
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseUserDto> addUsers(@Valid @RequestBody UserDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addUser(dto));
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseUserDto> getUser(@RequestParam(required = true) UUID id) {

        return ResponseEntity.ok(service.getUser(id));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseUserDto> updateUser(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(service.updateUser(dto));
    }

    @DeleteMapping("/details")
    public ResponseEntity<String> deleteUser(@RequestParam(required = true) UUID id) {
        service.deleteUser(id);
        return ResponseEntity.ok("Theatre deleted successfully");
    }
}
