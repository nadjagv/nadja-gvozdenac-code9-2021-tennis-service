package com.nadjagv.paymentservice.controller;

import com.nadjagv.paymentservice.dto.PaymentDTO;
import com.nadjagv.paymentservice.dto.PaymentMapper;
import com.nadjagv.paymentservice.service.PaymentService;
import com.nadjagv.playerservice.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/payments")
@Slf4j
@AllArgsConstructor
public class PaymentController {

    private final PaymentMapper paymentMapper;
    private final PaymentService paymentService;

    @PostMapping
    public void pay(@RequestBody final PaymentDTO paymentDTO) {
        paymentService.pay(paymentMapper.dtoToEntity(paymentDTO));
    }
}
