package com.booking.service;

import com.booking.dto.request.ScreenDto;

import com.booking.dto.response.ResponseScreenDto;

import com.booking.entity.Screen;
import com.booking.entity.Theatre;
import com.booking.exceptions.DuplicateScreenException;
import com.booking.exceptions.ScreenNotFoundException;
import com.booking.exceptions.TheatreNotFoundException;
import com.booking.mappers.ScreenMapper;
import com.booking.mappers.TheatreMapper;
import com.booking.repository.ScreensRepository;
import com.booking.repository.TheatreRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScreenService {

    @Autowired
    private ScreensRepository repo;

    @Autowired
    private TheatreRepository theatreRepository;



    public ResponseScreenDto addScreens(ScreenDto dto) {
        // Check duplicate name in city
        if (repo.existsByName(dto.name())) {
            throw new DuplicateScreenException("already exists");
        }

        Optional<Theatre> thetareOptional = theatreRepository.findById(dto.theatreId());
        try {
            Screen entity = ScreenMapper.mapScreenDToaEntity(dto);
            entity.setTheatre(thetareOptional.orElseThrow(() -> new TheatreNotFoundException("id not found "+ dto.theatreId())));
            entity = repo.save(entity);
            return ScreenMapper.mapScreenToDto(entity);
        }catch (Exception e) {
            throw new ScreenNotFoundException("screen not found " + dto.theatreId());

        }
    }

    public ResponseScreenDto getScreens(UUID uuid) {
            return repo.findById(uuid)
                    .map(ScreenMapper::mapScreenToDto)
                    .orElseThrow(() -> new ScreenNotFoundException("id not found "+ uuid));
    }

    public ResponseScreenDto updateScreens(ScreenDto dto) {
        Screen entity = repo.findById(dto.uuid())
                .orElseThrow(() -> new ScreenNotFoundException("id not found "+ dto.uuid()));
        entity = ScreenMapper.mapScreenDToaEntity(dto);
        Optional<Theatre> thetareOptional = theatreRepository.findById(dto.theatreId());
        entity.setTheatre(thetareOptional.orElseThrow(() -> new TheatreNotFoundException("id not found "+ dto.theatreId())));
        return ScreenMapper.mapScreenToDto(repo.save(entity));
    }

    public void deleteScreens(UUID uuid) {
            Optional<Screen> options =  repo.findById(uuid);
            if(options.isEmpty()) {
                throw new ScreenNotFoundException("id not found " + uuid);
            }
            repo.deleteById(options.get().getId());

    }
}
