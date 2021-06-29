package com.nadjagv.timeslotservice.dto;

import com.nadjagv.playerservice.domain.PaymentType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;


@Value
@Builder
public class TimeslotPaymentDTO {
    Long id;

    LocalDateTime start;

    LocalDateTime end;

    Long courtId;

    Long playerId;

    PaymentType paymentType;
    
}