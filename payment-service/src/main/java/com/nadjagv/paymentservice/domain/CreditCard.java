package com.nadjagv.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    String creditCardNumber;

    String cvc;

    LocalDate validDate;
}
