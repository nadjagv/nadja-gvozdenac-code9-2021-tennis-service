package com.nadjagv.timeslotservice.dto;

import com.nadjagv.timeslotservice.domain.Timeslot;
import com.nadjagv.timeslotservice.util.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TimeslotMapper implements Mapper<Timeslot, TimeslotDTO> {


    @Override
    public Timeslot dtoToEntity(TimeslotDTO dto) {
        return Timeslot.builder()
                .id(dto.getId())
                .courtId(dto.getCourtId())
                .playerId(dto.getPlayerId())
                .end(dto.getEnd())
                .start(dto.getStart())
                .build();
    }

    @Override
    public TimeslotDTO entityToDto(Timeslot entity) {
        return TimeslotDTO.builder()
                .id(entity.getId())
                .courtId(entity.getCourtId())
                .playerId(entity.getPlayerId())
                .end(entity.getEnd())
                .start(entity.getStart())
                .build();
    }
}
