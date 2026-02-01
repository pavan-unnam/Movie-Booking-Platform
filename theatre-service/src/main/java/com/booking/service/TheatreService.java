package com.booking.service;

import com.booking.dto.response.ResponseTheatreDto;
import com.booking.exceptions.DuplicateTheatreException;
import com.booking.exceptions.InvalidStateException;
import com.booking.exceptions.TheatreNotFoundException;
import com.booking.dto.request.TheatreDto;
import com.booking.entity.Theatre;
import com.booking.enums.TheatreStatus;
import com.booking.mappers.TheatreMapper;
import com.booking.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TheatreService {
    @Autowired
    private TheatreRepository repo;



    public ResponseTheatreDto onboardTheatre(TheatreDto dto) {
        // Check duplicate name in city
        if (repo.existsByNameAndCity(dto.name(), dto.city())) {
            throw new DuplicateTheatreException("already exists");
        }

        try {
            Theatre entity = TheatreMapper.mapDtoEntity(dto);
            Theatre finalEntity = entity;
            entity.getScreens().forEach(screen -> screen.setTheatre(finalEntity));
            entity = repo.save(entity);
            // Publish event
           // kafkaTemplate.send("theatre-onboarded", entity.getId().toString(), entity);
            return TheatreMapper.mapToDto(entity);
        } finally {

        }
    }

    public ResponseTheatreDto getTheatre(UUID uuid, String name) {
        if(StringUtils.isNoneBlank(name))
            return repo.findByName(name)
                .map(TheatreMapper::mapToDto)
                .orElseThrow(() -> new TheatreNotFoundException("id not found "+ name));
        else
            return repo.findById(uuid)
                    .map(TheatreMapper::mapToDto)
                    .orElseThrow(() -> new TheatreNotFoundException("id not found "+ name));
    }

    public ResponseTheatreDto updateTheatre(TheatreDto dto) {
        Theatre entity = repo.findById(dto.uuid())
                .orElseThrow(() -> new TheatreNotFoundException("id not found "+ dto.uuid()));
        entity = TheatreMapper.mapDtoEntity(dto);
        return TheatreMapper.mapToDto(repo.save(entity));
    }

    public void deleteTheatre(UUID uuid, String name) {
        if(StringUtils.isNoneBlank(name)) {
            Optional<Theatre> options = repo.findByName(name);
            if(options.isEmpty()) {
                throw new TheatreNotFoundException("id not found " + name);
            }
            repo.deleteById(options.get().getId());
        }
        else {
            Optional<Theatre> options =  repo.findById(uuid);
            if(options.isEmpty()) {
                throw new TheatreNotFoundException("id not found " + uuid);
        }
            repo.deleteById(options.get().getId());
        }
    }
}

