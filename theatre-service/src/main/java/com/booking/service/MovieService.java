package com.booking.service;

import com.booking.dto.request.MovieDto;

import com.booking.dto.response.ResponseMovieDto;

import com.booking.entity.Movie;

import com.booking.entity.Screen;
import com.booking.exceptions.DuplicateMovieException;

import com.booking.exceptions.InvalidStateException;
import com.booking.exceptions.MovieNotFoundException;

import com.booking.mappers.MovieMapper;

import com.booking.repository.MovieRepository;
import com.booking.repository.ScreensRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repo;

    @Autowired
    private ScreensRepository screensRepository;



    public ResponseMovieDto addMovies(MovieDto dto) {
        // Check duplicate name in city
        if (repo.existsByTitle(dto.title())) {
            throw new DuplicateMovieException("already exists");
        }

        try {
            Movie entity = MovieMapper.mapMovieDtoEntity(dto);
            Optional<Screen> screen = screensRepository.findById(dto.showTimes().stream().map(showtime -> showtime.screenId()).toList().getFirst());
            if(screen.isPresent())
                entity.getShowtimes().forEach(showtime -> showtime.setScreen(screen.get()));
            Movie finalEntity = entity;
            entity.getShowtimes().forEach(showtime -> showtime.setMovie(finalEntity));
            entity = repo.save(entity);
            // Publish event
            // kafkaTemplate.send("theatre-onboarded", entity.getId().toString(), entity);
            return MovieMapper.mapMovieToDto(entity);
        } catch (Exception e) {
            throw new MovieNotFoundException("movie not found " + dto.title());
        }
    }

    public ResponseMovieDto getMovies(UUID uuid, String title) {
        if(StringUtils.isNoneBlank(title))
            return repo.findByTitle(title)
                    .map(MovieMapper::mapMovieToDto)
                    .orElseThrow(() -> new MovieNotFoundException("id not found "+ title));
        else
            return repo.findById(uuid)
                    .map(MovieMapper::mapMovieToDto)
                    .orElseThrow(() -> new MovieNotFoundException("id not found "+ uuid));
    }

    public ResponseMovieDto updateMovies(MovieDto dto) {
        Movie entity = repo.findById(dto.uuid())
                .orElseThrow(() -> new MovieNotFoundException("id not found "+ dto.uuid()));

        if(ObjectUtils.isEmpty(entity)) {
            throw new InvalidStateException("invalid movie");
        }
        entity = MovieMapper.mapMovieDtoEntity(dto);
        Optional<Screen> screen = screensRepository.findById(dto.showTimes().stream().map(showtime -> showtime.screenId()).toList().getFirst());
        if(screen.isPresent())
            entity.getShowtimes().forEach(showtime -> showtime.setScreen(screen.get()));
        Movie finalEntity = entity;
        entity.getShowtimes().forEach(showtime -> showtime.setMovie(finalEntity));
        return MovieMapper.mapMovieToDto(repo.save(entity));
    }

    public void deleteMovies(UUID uuid, String name) {
        if(StringUtils.isNoneBlank(name)) {
            Optional<Movie> options = repo.findByTitle(name);
            if(options.isEmpty()) {
                throw new MovieNotFoundException("id not found " + name);
            }
            repo.deleteById(options.get().getId());
        }
        else {
            Optional<Movie> options =  repo.findById(uuid);
            if(options.isEmpty()) {
                throw new MovieNotFoundException("id not found " + uuid);
            }
            repo.deleteById(options.get().getId());
        }
    }
}
