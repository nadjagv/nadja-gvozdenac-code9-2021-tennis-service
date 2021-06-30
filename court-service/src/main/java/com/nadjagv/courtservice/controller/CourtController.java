package com.nadjagv.courtservice.controller;

import com.nadjagv.courtservice.client.CourtClient;
import com.nadjagv.courtservice.dto.CourtDTO;
import com.nadjagv.courtservice.dto.CourtMapper;
import com.nadjagv.courtservice.service.CourtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/courts")
@Slf4j
@AllArgsConstructor
public class CourtController implements CourtClient {

    private final CourtService courtService;
    private final CourtMapper courtMapper;

    @GetMapping
    public List<CourtDTO> getAll() {
        return courtService.findAll()
                .stream()
                .map(courtMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourtDTO getOne(@PathVariable("id") Long id) {
        return courtMapper.entityToDto(courtService.findCourtById(id));
    }

    @PostMapping
    public void createCourt(@RequestBody final CourtDTO courtDTO) {
        courtService.createCourt(courtMapper.dtoToEntity(courtDTO));
    }

    @PutMapping
    public void updateCourt(@RequestBody final CourtDTO courtDTO) {
        courtService.updateCourt(courtMapper.dtoToEntity(courtDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteCourt(@PathVariable("id") final Long id) {
        courtService.deleteCourtById(id);
    }

}
