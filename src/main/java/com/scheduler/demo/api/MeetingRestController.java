package com.scheduler.demo.api;

import com.scheduler.demo.dtos.BookMeetingDto;
import com.scheduler.demo.domain.entities.Meeting;
import com.scheduler.demo.services.MeetingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingRestController {
    private final MeetingService meetingService;

    @PostMapping
    public HttpEntity<?> bookMeeting(@RequestBody BookMeetingDto dto) {
        Meeting meeting = meetingService.bookMeeting(dto);

        var httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/meetings/" + meeting.getId().toString()).build().toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
