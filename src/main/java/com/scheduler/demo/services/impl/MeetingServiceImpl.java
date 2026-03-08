package com.scheduler.demo.services.impl;


import com.scheduler.demo.api.exceptions.DtoValidationException;
import com.scheduler.demo.domain.entities.TimeSlot;
import com.scheduler.demo.dtos.BookMeetingDto;
import com.scheduler.demo.domain.entities.Meeting;
import com.scheduler.demo.enumerations.TimeSlotStatus;
import com.scheduler.demo.repositories.MeetingRepository;
import com.scheduler.demo.repositories.TimeSlotRepository;
import com.scheduler.demo.services.MeetingService;
import com.scheduler.demo.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService {
    private final UserService userService;
    private final MeetingRepository meetingRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Transactional
    @Override
    public Meeting bookMeeting(BookMeetingDto dto) {
        TimeSlot timeSlot = timeSlotRepository.findById(dto.slotId()).orElseThrow();
        if (timeSlot.isBusy()) {
            throw new DtoValidationException("Slot is already busy");
        }

        Meeting meeting = new Meeting();
        meeting.setTitle(dto.title());
        meeting.setDescription(dto.description());
        meeting.setTimeSlot(timeSlot);

        meeting = meetingRepository.save(meeting);

        addParticipants(dto.participants(), meeting);

        timeSlot.setStatus(TimeSlotStatus.BUSY);
        timeSlot.setMeeting(meeting);

        return meeting;
    }

    private void addParticipants(List<UUID> participantIds, Meeting meeting) {
        participantIds.forEach(
                participant -> meeting.addParticipant(userService.getUser(participant)));
    }
}
