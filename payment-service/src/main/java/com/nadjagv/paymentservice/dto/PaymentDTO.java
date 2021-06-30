package com.nadjagv.paymentservice.dto;

import com.nadjagv.paymentservice.domain.PaymentType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PaymentDTO {
    Long id;

    Long playerId;

    PaymentType type;
}
