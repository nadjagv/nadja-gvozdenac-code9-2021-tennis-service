package com.nadjagv.paymentservice.payment;

import com.nadjagv.paymentservice.domain.Payment;

public interface PaymentInterface {
    Payment pay(Payment p);
}
