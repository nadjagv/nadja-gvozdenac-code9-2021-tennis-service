package com.nadjagv.notificationservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadjagv.notificationservice.email.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MessagesListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "timeslot-reservation-queue")
    public void receiveTimeslotMessage(String message) {
        TimeslotReservationMessage timeslotReservationMessage = null;
        try {
            timeslotReservationMessage = new ObjectMapper().readValue(message, TimeslotReservationMessage.class);
        } catch (JsonProcessingException e) {
            log.error("Error during reading message: %s", e);
            e.printStackTrace();
            return;
        }
        log.info("Message successfully received");
        notificationService.sendTimeslotReservation(timeslotReservationMessage.getStart(),
                timeslotReservationMessage.getEnd(), timeslotReservationMessage.getPlayerId(),
                timeslotReservationMessage.getCourtId());
    }
}
