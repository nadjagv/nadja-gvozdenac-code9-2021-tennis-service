package com.nadjagv.timeslotservice.messaging;

import com.nadjagv.timeslotservice.domain.Timeslot;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
@Slf4j
public class MessageFactory {

    public static TimeslotReservationMessage createTimeslotReservationMessage(Timeslot timeslot){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return TimeslotReservationMessage.builder()
                .start(timeslot.getStart().format(dtf))
                .end(timeslot.getEnd().format(dtf))
                .playerId(timeslot.getPlayerId().toString())
                .courtId(timeslot.getCourtId().toString())
                .build();
    }
}


