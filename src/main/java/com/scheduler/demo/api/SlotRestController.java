package com.scheduler.demo.api;

import com.scheduler.demo.dtos.CreateSlotDto;
import com.scheduler.demo.dtos.SlotDto;
import com.scheduler.demo.domain.entities.TimeSlot;
import com.scheduler.demo.services.SlotService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/slots")
@RequiredArgsConstructor
public class SlotRestController {
    private final SlotService slotService;

    @PostMapping
    public HttpEntity<?> createSlot(@PathVariable UUID userId, @RequestBody CreateSlotDto dto) {
        TimeSlot slot = slotService.createSlot(userId, dto);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/" + userId + "/slots/" + slot.getId().toString()).build().toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public List<SlotDto> getAvailability(
            @PathVariable UUID userId,
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        return slotService.getAvailability(userId, from, to)
                .stream()
                .map(availability -> new SlotDto(availability.getStartTime(), availability.getEndTime()))
                .toList();
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable UUID slotId) {
        slotService.deleteSlot(slotId);
        return ResponseEntity.noContent().build();
    }
}
