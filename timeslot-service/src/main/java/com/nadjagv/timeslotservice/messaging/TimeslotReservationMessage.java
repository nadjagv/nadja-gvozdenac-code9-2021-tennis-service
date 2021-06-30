package com.nadjagv.timeslotservice.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
public class TimeslotReservationMessage extends BaseMessage{
    @JsonProperty("start")
    String start;
    @JsonProperty("end")
    String end;
    @JsonProperty("courtId")
    String courtId;
    @JsonProperty("playerId")
    String playerId;
}
