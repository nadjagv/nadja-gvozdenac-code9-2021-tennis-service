package com.nadjagv.timeslotservice.service;

import com.nadjagv.timeslotservice.repository.TimeslotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;
}
