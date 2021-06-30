package com.nadjagv.timeslotservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {

    private final RabbitTemplate rabbitTemplate;

    private final Queue timeslotQueue;

    public void sendMessageTimeslotTopic(TimeslotReservationMessage message) {
        String textMessage = "{}";
        try {
            textMessage = new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("Error during mapping!");
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(timeslotQueue.getName(), textMessage);
        log.info("Message successfully published to topic '{}'!", timeslotQueue.getName());
    }

}