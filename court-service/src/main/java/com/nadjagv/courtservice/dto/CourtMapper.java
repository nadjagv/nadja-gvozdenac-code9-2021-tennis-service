package com.nadjagv.courtservice.dto;

import com.nadjagv.courtservice.domain.Court;
import com.nadjagv.courtservice.util.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CourtMapper implements Mapper<Court, CourtDTO> {
    @Override
    public Court dtoToEntity(CourtDTO dto) {
        return Court.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public CourtDTO entityToDto(Court entity) {
        return CourtDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
