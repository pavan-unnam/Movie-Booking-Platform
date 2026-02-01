package com.booking.service;

import com.booking.dto.request.BookingDto;
import com.booking.dto.response.ResponseBookingDto;
import com.booking.entity.Booking;
import com.booking.entity.Movie;
import com.booking.entity.Screen;
import com.booking.entity.Showtime;
import com.booking.entity.User;
import com.booking.enums.BookingStatus;
import com.booking.exceptions.SeatNotAvialbleEaxception;
import com.booking.mappers.BookingMapper;
import com.booking.mappers.ShowTimeMapper;
import com.booking.repository.BookingRepository;
import com.booking.repository.MovieRepository;
import com.booking.repository.ScreensRepository;
import com.booking.repository.ShowTimesRepository;
import com.booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowTimesRepository showTimesRepository;

    @Autowired
    private ScreensRepository screensRepository;

    @Autowired
    private MovieRepository movieRepository;

    public ResponseBookingDto createBooking(BookingDto dto) {
        Optional<Booking> booking = repo.findBySeatIdAndStatusAndShowtimeId(dto.seatId(),dto.status(),
                ShowTimeMapper.mapShowTimeDtoEntity(dto.showTime()).getId());
        if(booking.isEmpty()) {
           Showtime showtime = showTimesRepository.findById(dto.showTime().uuid())
                   .orElseThrow(() -> new SeatNotAvialbleEaxception("showtime id not found " + dto.showTime().uuid()));
           Screen screen = screensRepository.findById(dto.showTime().screenId())
                   .orElseThrow(() -> new SeatNotAvialbleEaxception("screen id not found " + dto.showTime().screenId()));
            User user = userRepository.findById(dto.user().uuid())
                    .orElseThrow(() -> new SeatNotAvialbleEaxception("user id not found " + dto.user().uuid()));
            Movie movie = movieRepository.findById(showtime.getMovieId())
                    .orElseThrow(() -> new SeatNotAvialbleEaxception("user id not found " + dto.user().uuid()));
            Booking entity = BookingMapper.mapToDtoEntity(dto);
            showtime.setScreen(screen);
            showtime.setMovie(movie);
            entity.setShowtime(showtime);
            entity.setUser(user);
            entity = repo.save(entity);
            return BookingMapper.mapToDto(entity);
        } else {
            throw new SeatNotAvialbleEaxception("seat is alrerady booked");
        }
    }

    public ResponseBookingDto getBookingDetails(UUID uuid) {
        Booking entity = repo.findById(uuid)
                .orElseThrow(() -> new SeatNotAvialbleEaxception("booking id not found " + uuid));
        return BookingMapper.mapToDto(entity);
    }

    public ResponseBookingDto cancelBooking(UUID uuid) {
        Booking entity = repo.findById(uuid)
                .orElseThrow(() -> new SeatNotAvialbleEaxception("booking id not found " + uuid));
        entity.setStatus(BookingStatus.CANCELLED);
        return BookingMapper.mapToDto(repo.save(entity));
    }

    public void deleteBookingDetails(UUID uuid) {
        Booking entity = repo.findById(uuid)
                .orElseThrow(() -> new SeatNotAvialbleEaxception("booking id not found " + uuid));
        repo.delete(entity);
    }

}

