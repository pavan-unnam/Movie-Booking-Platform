package com.booking.mappers;

import com.booking.dto.request.MovieDto;
import com.booking.dto.response.ResponseMovieDto;
import com.booking.entity.Movie;


public class MovieMapper {
    public static ResponseMovieDto mapMovieToDto(Movie movie) {
        return new ResponseMovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getGenres(),
                movie.getDurationMinutes(),
                movie.getReleaseDate(),
                movie.getRating(),
                movie.getCreatedAt()
               // movie.getShowtimes().stream().map(ShowTimeMapper::mapShowTimeDto).toList()
        );
    }

    public static Movie mapMovieDtoEntity(MovieDto dto) {
        return Movie.builder()
                .title(dto.title())
                .description(dto.description())
                .genres(dto.genres())
                .durationMinutes(dto.durationMinutes())
                .releaseDate(dto.releaseDate())
                .rating(dto.rating())
                .language(dto.language())
                .createdAt(dto.createdAt())
                .showtimes(dto.showTimes().stream().map(ShowTimeMapper::mapShowTimeDtoEntity)
                        .toList())
                .build();
    }
}
