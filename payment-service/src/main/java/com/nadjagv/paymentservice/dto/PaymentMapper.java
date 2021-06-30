package com.nadjagv.paymentservice.dto;

import com.nadjagv.paymentservice.domain.Payment;
import com.nadjagv.paymentservice.util.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper implements Mapper<Payment, PaymentDTO> {
    @Override
    public Payment dtoToEntity(PaymentDTO dto) {
        return Payment.builder()
                .id(dto.getId())
                .playerId(dto.getPlayerId())
                .type(dto.getType())
                .build();
    }

    @Override
    public PaymentDTO entityToDto(Payment entity) {
        return PaymentDTO.builder()
                .id(entity.getId())
                .playerId(entity.getPlayerId())
                .type(entity.getType())
                .build();
    }
}
