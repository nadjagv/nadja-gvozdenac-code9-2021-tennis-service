package com.nadjagv.paymentservice.dto;

import com.nadjagv.paymentservice.domain.PaymentType;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import java.time.LocalDate;

@Value
@Builder
public class PaymentAllDTO {
    Long id;

    Long playerId;

    PaymentType type;

    String creditCardNumber;

    String cvc;

    LocalDate validDate;
}
