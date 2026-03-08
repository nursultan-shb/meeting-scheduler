package com.scheduler.demo.services;

import com.scheduler.demo.dtos.CreateSlotDto;
import com.scheduler.demo.domain.entities.TimeSlot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SlotService {
    TimeSlot createSlot(UUID userId, CreateSlotDto dto);

    List<TimeSlot> getAvailability(UUID userId, Instant from, Instant to);

    void deleteSlot(UUID slotId);
}
