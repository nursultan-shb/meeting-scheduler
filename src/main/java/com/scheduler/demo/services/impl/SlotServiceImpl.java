package com.scheduler.demo.services.impl;

import com.scheduler.demo.api.exceptions.DtoValidationException;
import com.scheduler.demo.dtos.CreateSlotDto;
import com.scheduler.demo.domain.entities.TimeSlot;
import com.scheduler.demo.domain.entities.User;
import com.scheduler.demo.enumerations.TimeSlotStatus;
import com.scheduler.demo.repositories.TimeSlotRepository;
import com.scheduler.demo.services.SlotService;
import com.scheduler.demo.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SlotServiceImpl implements SlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final UserService userService;

    @Transactional
    @Override
    public TimeSlot createSlot(UUID userId, CreateSlotDto dto) {
        validateSlotCreation(userId, dto);
        User user = userService.getUser(userId);

        TimeSlot slot = new TimeSlot();
        slot.setUser(user);
        slot.setStartTime(dto.startTime());
        slot.setEndTime(dto.endTime());
        slot.setStatus(TimeSlotStatus.FREE);

        return timeSlotRepository.save(slot);
    }

    public List<TimeSlot> getAvailability(UUID userId, Instant from, Instant to) {
        return timeSlotRepository.findSlotsInRange(userId, from, to);
    }

    public void deleteSlot(UUID slotId) {
        timeSlotRepository.deleteById(slotId);
    }

    private void validateSlotCreation(UUID userId, CreateSlotDto dto) {
        if (dto.startTime().isAfter(dto.endTime())) {
            throw new DtoValidationException("Start time is after end time");
        }
        if (timeSlotRepository.existsOverlappingSlot(userId, dto.startTime(), dto.endTime())) {
            throw new IllegalStateException("Overlapping slot exists");
        }
    }
}
