package com.scheduler.demo.repositories;

import com.scheduler.demo.domain.entities.TimeSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, UUID> {
    @Query("""
        SELECT s FROM TimeSlot s
        WHERE s.user.id = :userId
        AND s.startTime >= :from
        AND s.endTime <= :to
        ORDER BY s.startTime
    """)
    List<TimeSlot> findSlotsInRange(
            UUID userId,
            Instant from,
            Instant to
    );

    @Query("""
        SELECT COUNT(s) > 0 FROM TimeSlot s
        WHERE s.user.id = :userId
        AND s.startTime < :end
        AND s.endTime > :start
    """)
    boolean existsOverlappingSlot(
            UUID userId,
            Instant start,
            Instant end
    );
}
