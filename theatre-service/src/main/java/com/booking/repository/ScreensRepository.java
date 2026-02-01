package com.booking.repository;

import com.booking.entity.Screen;
import com.booking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScreensRepository extends JpaRepository<Screen, UUID> {
    boolean existsByName(String name);
    Optional<Screen> findById(UUID id);
}
