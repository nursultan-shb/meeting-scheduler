package com.scheduler.demo.domain.entities;

import com.scheduler.demo.enumerations.TimeSlotStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Time_Slots")
public class TimeSlot extends BaseEntity {
    @Id
    @GeneratedValue(generator = "use-or-generate")
    UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "start_time")
    Instant startTime;
    @Column(name = "end_time")
    Instant endTime;

    @Enumerated(EnumType.STRING)
    TimeSlotStatus status;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    Meeting meeting;

    public boolean isBusy() {
        return this.getStatus() == TimeSlotStatus.BUSY;
    }
}
