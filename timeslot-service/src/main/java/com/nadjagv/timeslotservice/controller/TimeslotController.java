package com.nadjagv.timeslotservice.controller;

import com.nadjagv.timeslotservice.dto.TimeslotDTO;
import com.nadjagv.timeslotservice.dto.TimeslotMapper;
import com.nadjagv.timeslotservice.service.TimeslotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/timeslots")
@Slf4j
@AllArgsConstructor
public class TimeslotController {
    private final TimeslotMapper timeslotMapper;
    private final TimeslotService timeslotService;

    @GetMapping
    public List<TimeslotDTO> getAll() {
        return timeslotService.findAll()
                .stream()
                .map(timeslotMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TimeslotDTO getOne(@PathVariable("id") Long id) {
        return timeslotMapper.entityToDto(timeslotService.findTimeslotById(id));
    }


    @PutMapping
    public void updateTimeslot(@RequestBody final TimeslotDTO timeslotDTO) {
        timeslotService.updateTimeslot(timeslotMapper.dtoToEntity(timeslotDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteTimeslot(@PathVariable("id") final Long id) {
        timeslotService.deleteTimeslotById(id);
    }

    @PostMapping
    public void reserveTimeslot(@RequestBody final TimeslotDTO dto) {
        timeslotService.reserveTimeslot(timeslotMapper.dtoToEntity(dto));
    }
}
