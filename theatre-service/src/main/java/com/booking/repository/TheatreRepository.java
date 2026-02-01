package com.booking.repository;

import com.booking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, UUID> {
    boolean existsByNameAndCity(String name, String city);
    //@Lock(LockModeType.OPTIMISTIC)
    Optional<Theatre> findById(UUID id);

    Optional<Theatre> findByName(String name);
}

