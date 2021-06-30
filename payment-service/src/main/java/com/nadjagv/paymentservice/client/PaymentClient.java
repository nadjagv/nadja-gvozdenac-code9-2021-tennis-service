package com.nadjagv.paymentservice.client;

import com.nadjagv.paymentservice.dto.PaymentAllDTO;
import feign.RequestLine;

import java.util.List;

public interface PaymentClient {
    @RequestLine("GET /api/payments")
    List<PaymentAllDTO> getAll();
}
