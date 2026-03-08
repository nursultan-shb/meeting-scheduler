package com.scheduler.demo.dtos;

import java.time.Instant;

public record CreateSlotDto(Instant startTime, Instant endTime) {
}
