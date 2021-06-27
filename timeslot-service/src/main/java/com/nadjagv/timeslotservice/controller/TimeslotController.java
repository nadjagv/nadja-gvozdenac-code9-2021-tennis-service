package com.nadjagv.timeslotservice.controller;

import com.nadjagv.timeslotservice.dto.TimeslotDTO;
import com.nadjagv.timeslotservice.dto.TimeslotMapper;
import com.nadjagv.timeslotservice.service.TimeslotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/timeslots")
@Slf4j
@AllArgsConstructor
public class TimeslotController {
    private final TimeslotMapper timeslotMapper;
    private final TimeslotService timeslotService;


    @PostMapping
    public void reserveTimeslot(@RequestBody final TimeslotDTO dto) {
        timeslotService.reserveTimeslot(dto);
    }
}
