package com.scheduler.demo.services;

import com.scheduler.demo.dtos.BookMeetingDto;
import com.scheduler.demo.domain.entities.Meeting;

public interface MeetingService {
    Meeting bookMeeting(BookMeetingDto dto);
}
