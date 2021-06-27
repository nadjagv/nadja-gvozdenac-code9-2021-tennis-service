package com.nadjagv.playerservice.controller;


import com.nadjagv.playerservice.dto.PlayerDTO;
import com.nadjagv.playerservice.dto.PlayerMapper;
import com.nadjagv.playerservice.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/players")
@Slf4j
@AllArgsConstructor
public class PlayerController {

    private final PlayerMapper playerMapper;
    private final PlayerService playerService;

    @GetMapping
    public List<PlayerDTO> getAll() {
        return playerService.findAll()
                .stream()
                .map(playerMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlayerDTO getOne(@PathVariable("id") Long id) {
        return playerMapper.entityToDto(playerService.findPlayerById(id));
    }

    @PostMapping
    public void createPlayer(@RequestBody final PlayerDTO playerDTO) {
        playerService.createPlayer(playerMapper.dtoToEntity(playerDTO));
    }

    @PutMapping
    public void updatePlayer(@RequestBody final PlayerDTO playerDTO) {
        playerService.updatePlayer(playerMapper.dtoToEntity(playerDTO));
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable("id") final Long id) {
        playerService.deletePlayerById(id);
    }
}
