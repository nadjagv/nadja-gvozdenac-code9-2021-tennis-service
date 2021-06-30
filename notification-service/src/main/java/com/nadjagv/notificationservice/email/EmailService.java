package com.nadjagv.notificationservice.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendMailTimeslotReservation(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gvozdenac.code9@gmail.com");
        message.setTo(to);
        message.setSubject(String.format("Timeslot reservation"));
        message.setText(text);

        mailSender.send(message);
    }

    public String generateTimeslotReservationText(String start, String end, String playerId, String courtId) {

        return String.format("New reservation: \n" +
                "start: %s\n" +
                "end: %s\n" +
                "player id: %s\n" +
                "court id: %s\n", start, end, playerId, courtId);
    }

}
