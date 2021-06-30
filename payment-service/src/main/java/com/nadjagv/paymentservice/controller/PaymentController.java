package com.nadjagv.paymentservice.controller;

import com.nadjagv.paymentservice.dto.PaymentAllDTO;
import com.nadjagv.paymentservice.dto.PaymentDTO;
import com.nadjagv.paymentservice.dto.PaymentMapper;
import com.nadjagv.paymentservice.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/payments")
@Slf4j
@AllArgsConstructor
public class PaymentController {

    private final PaymentMapper paymentMapper;
    private final PaymentService paymentService;

    @GetMapping
    public List<PaymentDTO> getAll() {
        return paymentService.findAll()
                .stream()
                .map(paymentMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PaymentDTO getOne(@PathVariable("id") Long id) {
        return paymentMapper.entityToDto(paymentService.findPaymentById(id));
    }

    @PostMapping
    public void pay(@RequestBody final PaymentAllDTO dto) {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .playerId(dto.getPlayerId())
                .type(dto.getType())
                .id(dto.getId())
                .build();
        paymentService.pay(paymentMapper.dtoToEntity(paymentDTO));
    }

}
