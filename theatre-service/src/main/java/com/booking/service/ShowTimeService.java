package com.booking.service;

import com.booking.dto.request.ShowTimeDto;
import com.booking.dto.response.ResponseShowTimeDto;
import com.booking.entity.Movie;
import com.booking.entity.Screen;
import com.booking.entity.Showtime;
import com.booking.exceptions.InvalidStateException;
import com.booking.exceptions.ShowtimeNotFoundException;
import com.booking.exceptions.TheatreNotFoundException;
import com.booking.mappers.ShowTimeMapper;
import com.booking.repository.MovieRepository;
import com.booking.repository.ScreensRepository;
import com.booking.repository.ShowTimesRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ShowTimeService {

    @Autowired
    private ShowTimesRepository repo;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreensRepository screensRepository;



    public ResponseShowTimeDto addShowTimes(ShowTimeDto dto) {
        try {
           Movie movieEntity =  movieRepository.findById(dto.movieId())
                    .orElseThrow(() -> new ShowtimeNotFoundException("movie id not found " + dto.movieId()));
            Screen screenEntity = screensRepository.findById(dto.screenId())
                    .orElseThrow(() -> new ShowtimeNotFoundException("movie id not found " + dto.movieId()));
            Showtime entity = ShowTimeMapper.mapShowTimeDtoEntity(dto);
            entity.setMovie(movieEntity);
            entity.setScreen(screenEntity);
            entity = repo.save(entity);
            // Publish event
            // kafkaTemplate.send("theatre-onboarded", entity.getId().toString(), entity);
            return ShowTimeMapper.mapShowTimeDto(entity);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseShowTimeDto getShowTimes(UUID uuid) {
            return repo.findById(uuid)
                    .map(ShowTimeMapper::mapShowTimeDto)
                    .orElseThrow(() -> new ShowtimeNotFoundException("id not found "+ uuid));
    }

    public ResponseShowTimeDto updateShowTimes(ShowTimeDto dto) {
        Showtime entity = repo.findById(dto.uuid())
                .orElseThrow(() -> new TheatreNotFoundException("id not found "+ dto.uuid()));

        Movie movieEntity =  movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new ShowtimeNotFoundException("movie id not found " + dto.movieId()));
        Screen screenEntity = screensRepository.findById(dto.screenId())
                .orElseThrow(() -> new ShowtimeNotFoundException("movie id not found " + dto.movieId()));
        if (ObjectUtils.isEmpty(entity)) {
            throw new InvalidStateException("status not approved");
        }
        entity = ShowTimeMapper.mapShowTimeDtoEntity(dto);
        entity.setMovie(movieEntity);
        entity.setScreen(screenEntity);
        return ShowTimeMapper.mapShowTimeDto(repo.save(entity));
    }

    public void deleteShowTimes(UUID uuid) {
            Optional<Showtime> options =  repo.findById(uuid);
            if(options.isEmpty()) {
                throw new ShowtimeNotFoundException("id not found " + uuid);
            }
            repo.deleteById(options.get().getId());
    }
}
