package com.nadjagv.playerservice.client;

import com.nadjagv.playerservice.dto.PlayerDTO;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface PlayerClient {
    @RequestLine("GET /api/players")
    List<PlayerDTO> getAll();

    @RequestLine("GET /api/players/{id}")
    PlayerDTO getOne(@PathVariable("id") Long id);

}
