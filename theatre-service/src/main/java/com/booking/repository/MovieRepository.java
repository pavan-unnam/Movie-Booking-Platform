package com.booking.repository;

import com.booking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    boolean existsByTitle(String name);
    Optional<Movie> findById(UUID id);
    Optional<Movie> findByTitle(String name);
}
