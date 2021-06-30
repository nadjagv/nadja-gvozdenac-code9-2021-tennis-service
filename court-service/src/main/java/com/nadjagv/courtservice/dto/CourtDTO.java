package com.nadjagv.courtservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourtDTO {
    Long id;
    String name;
}
