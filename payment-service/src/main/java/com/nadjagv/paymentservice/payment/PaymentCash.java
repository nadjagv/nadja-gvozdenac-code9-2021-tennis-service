package com.nadjagv.paymentservice.payment;

import com.nadjagv.paymentservice.domain.Payment;
import com.nadjagv.paymentservice.domain.PaymentType;

public class PaymentCash implements PaymentInterface{
    @Override
    public Payment pay(Payment p) {
        Payment newPayment = Payment.builder()
                .playerId(p.getPlayerId())
                .type(PaymentType.CASH)
                .build();
        return newPayment;
    }
}
