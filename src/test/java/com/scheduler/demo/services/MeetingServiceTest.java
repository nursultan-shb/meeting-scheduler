package com.scheduler.demo.services;

import com.scheduler.demo.domain.entities.TimeSlot;
import com.scheduler.demo.dtos.BookMeetingDto;
import com.scheduler.demo.domain.entities.Meeting;
import com.scheduler.demo.domain.entities.User;
import com.scheduler.demo.enumerations.TimeSlotStatus;
import com.scheduler.demo.repositories.MeetingRepository;
import com.scheduler.demo.repositories.TimeSlotRepository;
import com.scheduler.demo.services.impl.MeetingServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private TimeSlotRepository timeSlotRepository;

    @InjectMocks
    private MeetingServiceImpl testee;

    @Test
    public void bookMeetingTest() {
        User participant = new User();
        participant.setId(UUID.randomUUID());

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(UUID.randomUUID());

        BookMeetingDto dto = new BookMeetingDto("Meeting", "Sprint planning",
                timeSlot.getId(), List.of(participant.getId()));

        when(userService.getUser(participant.getId())).thenReturn(participant);
        when(meetingRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(timeSlotRepository.findById(dto.slotId())).thenReturn(Optional.of(timeSlot));

        Meeting result = testee.bookMeeting(dto);

        assertEquals(dto.title(), result.getTitle());
        assertEquals(dto.description(), result.getDescription());
        assertEquals(TimeSlotStatus.BUSY, result.getTimeSlot().getStatus());
    }
}
