package com.nadjagv.timeslotservice.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TimeslotDTO {
    Long id;

    LocalDateTime start;

    LocalDateTime end;

    Long courtId;

    Long playerId;
}
