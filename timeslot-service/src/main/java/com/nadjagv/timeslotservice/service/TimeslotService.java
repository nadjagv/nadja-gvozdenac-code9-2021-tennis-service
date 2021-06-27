package com.nadjagv.timeslotservice.service;

import com.nadjagv.timeslotservice.domain.Timeslot;
import com.nadjagv.timeslotservice.dto.TimeslotDTO;
import com.nadjagv.timeslotservice.exception.InvalidTimeslotDateTimeException;
import com.nadjagv.timeslotservice.exception.PlayerAlreadyReservedException;
import com.nadjagv.timeslotservice.repository.TimeslotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;

    //public void delete

    public void reserveTimeslot(TimeslotDTO dto){


        //preuzmi igraca preko player service

        //check player in the day
        checkPlayer(dto);
        //check date in the future, start before end
        checkDateValidity(dto);
        //check timeslot duration
        checkTimeslotDuration(dto);
        //check if overlaps with another timeslot on the court
        checkTimeslotsOverlap(dto);
        //check if timeslot is in working hours
        checkWorkingHours(dto);
        //check start and end date
        Timeslot newTimeslot = Timeslot.builder()
                .courtId(dto.getCourtId())
                .playerId(dto.getPlayerId())
                .start(dto.getStart())
                .end(dto.getEnd())
                .build();
        timeslotRepository.save(newTimeslot);



    }
    public void checkPlayer(TimeslotDTO dto){
        List<Timeslot> playersTimeslots = timeslotRepository.findAllByPlayerId(dto.getPlayerId());
        playersTimeslots.stream().forEach(timeslot ->{
            if (timeslot.getStart().toLocalDate().isEqual(dto.getStart().toLocalDate())){
                throw new PlayerAlreadyReservedException
                        (String.format("Player already has a reservation for chosen day."));

            }
        });
    }

    public void checkTimeslotDuration(TimeslotDTO dto){
        long duration = getDateTimeDiff(dto.getStart(), dto.getEnd(), TimeUnit.MINUTES);

        if (duration < 30) {
            throw new InvalidTimeslotDateTimeException
                    (String.format("Minimum timeslot duration is 30 minutes, but duration is %d minutes.", duration));
        }else if (duration > 120){
            throw new InvalidTimeslotDateTimeException
                    (String.format("Maximum timeslot duration is 2 hours, but duration is %d hours.", TimeUnit.MINUTES.toHours(duration)));
        }
    }

    public void checkDateValidity(TimeslotDTO dto){
        if (dto.getStart().isBefore(LocalDateTime.now())){
            throw new InvalidTimeslotDateTimeException
                    (String.format("Timeslot must be in the future, but it's start is in the past."));

        }
        if (dto.getStart().isAfter(dto.getEnd())){
            throw new InvalidTimeslotDateTimeException
                    (String.format("Chosen end time for timeslot is before the chosen start time."));

        }
    }

    public void checkTimeslotsOverlap(TimeslotDTO dto){
        List<Timeslot> overlappingTimeslots = timeslotRepository.findAllTimeslotsByCourtAndTimeOverlap(dto.getCourtId(),
                dto.getStart(), dto.getEnd());
        if(!overlappingTimeslots.isEmpty()){
            throw new InvalidTimeslotDateTimeException
                    (String.format("Timeslot is overlapping with another timeslot."));

        }
    }

    public void checkWorkingHours(TimeslotDTO dto){
        DayOfWeek day = dto.getStart().getDayOfWeek();

        LocalTime startTimeWorkingDays = LocalTime.of(18, 0, 0);
        LocalTime endTimeWorkingDays = LocalTime.of(23, 0, 0);

        LocalTime startTimeWeekend = LocalTime.of(17, 0, 0);
        LocalTime endTimeWeekend = LocalTime.of(22, 0, 0);

        if(day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)){
            if (dto.getStart().toLocalTime().isBefore(startTimeWeekend) || dto.getEnd().toLocalTime().isAfter(endTimeWeekend)){
                throw new InvalidTimeslotDateTimeException
                        (String.format("Timeslot chosen time not in weekend working hours."));
            }
        }else{
            if (dto.getStart().toLocalTime().isBefore(startTimeWorkingDays) || dto.getEnd().toLocalTime().isAfter(endTimeWorkingDays)){
                throw new InvalidTimeslotDateTimeException
                        (String.format("Timeslot chosen time not in working days working hours."));
            }
        }
    }

    public static long getDateTimeDiff(LocalDateTime start, LocalDateTime end, TimeUnit timeUnit) {
        long duration = Duration.between(start, end).toMillis();
        return timeUnit.convert(duration,TimeUnit.MILLISECONDS);
    }


}
