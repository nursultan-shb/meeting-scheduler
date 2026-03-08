package com.scheduler.demo.services;

import com.scheduler.demo.dtos.CreateSlotDto;
import com.scheduler.demo.domain.entities.TimeSlot;
import com.scheduler.demo.domain.entities.User;
import com.scheduler.demo.enumerations.TimeSlotStatus;
import com.scheduler.demo.repositories.TimeSlotRepository;
import com.scheduler.demo.services.impl.SlotServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SlotServiceTest {
    @Mock
    private TimeSlotRepository timeSlotRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private SlotServiceImpl testee;

    @Test
    public void createSlotTest() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        CreateSlotDto dto = new CreateSlotDto(Instant.MIN, Instant.MAX);

        when(userService.getUser(userId)).thenReturn(user);
        when(timeSlotRepository.existsOverlappingSlot(eq(userId), any(Instant.class), any(Instant.class))).thenReturn(false);
        when(timeSlotRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        TimeSlot result = testee.createSlot(userId, dto);

        assertEquals(userId, result.getUser().getId());
        assertEquals(dto.startTime(), result.getStartTime());
        assertEquals(dto.endTime(), result.getEndTime());
        assertEquals(TimeSlotStatus.FREE, result.getStatus());
    }

    @Test
    public void deleteSlotTest() {
        UUID userId = UUID.randomUUID();

        testee.deleteSlot(userId);

        verify(timeSlotRepository, times(1)).deleteById(userId);
    }
}
