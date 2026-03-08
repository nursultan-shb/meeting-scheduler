package com.scheduler.demo.dtos;

import java.time.Instant;

public record SlotDto(Instant startTime, Instant endTime) {

}
