package com.booking.service;

import com.booking.dto.request.TheatreDto;
import com.booking.dto.request.UserDto;
import com.booking.dto.response.ResponseTheatreDto;
import com.booking.dto.response.ResponseUserDto;
import com.booking.entity.Theatre;
import com.booking.entity.User;
import com.booking.enums.TheatreStatus;
import com.booking.exceptions.DuplicateTheatreException;
import com.booking.exceptions.DuplicateUserException;
import com.booking.exceptions.InvalidStateException;
import com.booking.exceptions.TheatreNotFoundException;
import com.booking.exceptions.UserNotFoundException;
import com.booking.mappers.TheatreMapper;
import com.booking.mappers.UserMapping;
import com.booking.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;


    public ResponseUserDto addUser(UserDto dto) {
        // Check duplicate name in city
        if (repo.existsByEmail(dto.email())) {
            throw new DuplicateUserException("already exists");
        }

        try {
            User entity = UserMapping.mapUserToDtoEntity(dto);
            entity = repo.save(entity);
            // Publish event
            // kafkaTemplate.send("theatre-onboarded", entity.getId().toString(), entity);
            return UserMapping.mapUserToDto(entity);
        } finally {

        }
    }

    public ResponseUserDto getUser(UUID uuid) {
            return repo.findById(uuid)
                    .map(UserMapping::mapUserToDto)
                    .orElseThrow(() -> new UserNotFoundException("id not found "+ uuid));
    }

    public ResponseUserDto updateUser(UserDto dto) {
        User entity = repo.findById(dto.uuid())
                .orElseThrow(() -> new UserNotFoundException("id not found "+ dto.uuid()));
        if (ObjectUtils.isNotEmpty(entity)) {
            entity = UserMapping.mapUserToDtoEntity(dto);
            return UserMapping.mapUserToDto(repo.save(entity));
        }
        return null;
    }

    public void deleteUser(UUID uuid) {
            Optional<User> options =  repo.findById(uuid);
            if(options.isEmpty()) {
                throw new UserNotFoundException("id not found " + uuid);
            }
            repo.deleteById(options.get().getId());
    }
}
