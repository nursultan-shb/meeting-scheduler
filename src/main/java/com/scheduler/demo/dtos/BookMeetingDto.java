package com.scheduler.demo.dtos;

import java.util.List;
import java.util.UUID;

public record BookMeetingDto(String title, String description,
                             UUID slotId, List<UUID> participants) {
}
