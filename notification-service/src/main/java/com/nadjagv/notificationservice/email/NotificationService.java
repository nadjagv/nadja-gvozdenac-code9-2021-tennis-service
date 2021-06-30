package com.nadjagv.notificationservice.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final EmailService emailService;
//todo change from email
    public void sendTimeslotReservation(String start, String end, String playerId, String courtId){
        String message = emailService.generateTimeslotReservationText(start, end, playerId, courtId);
        emailService.sendMailTimeslotReservation("gvozdenac.code9@gmail.com", message);
    }
}
