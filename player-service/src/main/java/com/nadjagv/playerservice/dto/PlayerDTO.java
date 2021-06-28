package com.nadjagv.playerservice.dto;

import com.nadjagv.playerservice.domain.PaymentType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
public class PlayerDTO {
    Long id;

    String email;

    String firstName;

    String lastName;

    Date dateOfBirth;

    PaymentType paymentType;

    Boolean paid;
}
