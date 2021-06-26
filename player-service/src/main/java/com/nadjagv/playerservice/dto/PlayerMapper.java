package com.nadjagv.playerservice.dto;

import com.nadjagv.playerservice.domain.Player;
import com.nadjagv.playerservice.util.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper implements Mapper<Player, PlayerDTO> {
    @Override
    public Player dtoToEntity(PlayerDTO dto) {
        return Player.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .dateOfBirth(dto.getDateOfBirth())
                .totalTimeslots(dto.getTotalTimeslots())
                .paymentType(dto.getPaymentType())
                .paid(dto.getPaid())
                .build();

    }

    @Override
    public PlayerDTO entityToDto(Player entity) {
        return null;
    }
}
