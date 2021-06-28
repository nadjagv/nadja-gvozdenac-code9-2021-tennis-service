package com.nadjagv.courtservice.client;

import com.nadjagv.courtservice.dto.CourtDTO;
import feign.RequestLine;

import java.util.List;

public interface CourtClient {
    @RequestLine("GET /api/courts")
    List<CourtDTO> getAll();
}
