package com.nadjagv.paymentservice.dto;

import com.nadjagv.paymentservice.domain.CreditCard;

public class CreditCardMapper{

    public CreditCard dtoToEntity(PaymentAllDTO dto) {
        return CreditCard.builder()
                .cvc(dto.getCvc())
                .validDate(dto.getValidDate())
                .creditCardNumber(dto.getCreditCardNumber())
                .build();
    }

}
