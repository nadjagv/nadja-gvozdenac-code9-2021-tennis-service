package com.nadjagv.timeslotservice.service;

import com.nadjagv.courtservice.client.CourtClient;
import com.nadjagv.courtservice.dto.CourtDTO;
import com.nadjagv.paymentservice.client.PaymentClient;
import com.nadjagv.paymentservice.dto.PaymentDTO;
import com.nadjagv.playerservice.client.PlayerClient;
import com.nadjagv.playerservice.domain.Player;
import com.nadjagv.playerservice.dto.PlayerDTO;
import com.nadjagv.timeslotservice.domain.Timeslot;
import com.nadjagv.timeslotservice.dto.TimeslotDTO;
import com.nadjagv.timeslotservice.exception.AlreadyExistsException;
import com.nadjagv.timeslotservice.exception.InvalidTimeslotDateTimeException;
import com.nadjagv.timeslotservice.exception.NotFoundException;
import com.nadjagv.timeslotservice.exception.PlayerAlreadyReservedException;
import com.nadjagv.timeslotservice.messaging.MessageFactory;
import com.nadjagv.timeslotservice.messaging.MessageService;
import com.nadjagv.timeslotservice.messaging.TimeslotReservationMessage;
import com.nadjagv.timeslotservice.repository.TimeslotRepository;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final MessageService messageService;

    private final PlayerClient playerClient = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(PlayerClient.class, "http://localhost:8081");

    private final CourtClient courtClient = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(CourtClient.class, "http://localhost:8080");

    private final PaymentClient paymentClient = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(PaymentClient.class, "http://localhost:8084");


    public Timeslot findTimeslotById(Long id){
        return timeslotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Timeslot not found."));
    }

    public List<Timeslot> findAll(){
        return timeslotRepository.findAll();
    }

    public void updateTimeslot(Timeslot timeslot) {
        Timeslot existing = timeslotRepository.getById(timeslot.getId());
        if (existing != null) {

            checkPlayer(timeslot);
            //check date in the future, start before end
            checkDateValidity(timeslot);
            //check timeslot duration
            checkTimeslotDuration(timeslot);
            //check if overlaps with another timeslot on the court
            checkTimeslotsOverlap(timeslot);
            //check if timeslot is in working hours
            checkWorkingHours(timeslot);

            Timeslot updated = Timeslot.builder()
                    .id(existing.getId())
                    .courtId(timeslot.getCourtId())
                    .playerId(timeslot.getPlayerId())
                    .end(timeslot.getEnd())
                    .start(timeslot.getStart())
                    .build();
            timeslotRepository.save(updated);
        } else {
            throw new NotFoundException("Cannot update timeslot that is not in database.");
        }
    }

    public void deleteTimeslotById(Long id) {
        Timeslot existing = timeslotRepository.getById(id);
        if (existing != null) {
            timeslotRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Timeslot with id %d does not exist.", id));
        }
    }


    public void reserveTimeslot(Timeslot timeslot){

        List<PlayerDTO> allPlayers = playerClient.getAll();
        PlayerDTO player =  allPlayers.stream()
                .filter(p -> p.getId() == timeslot.getPlayerId())
                .findFirst()
                .orElse(null);
        if(player == null){
            throw new NotFoundException("Player does not exist.");
        }

        List<CourtDTO> allCourts = courtClient.getAll();
        CourtDTO court =  allCourts.stream()
                .filter(c -> c.getId() == timeslot.getCourtId())
                .findFirst()
                .orElse(null);
        if(court == null){
            throw new NotFoundException("Court does not exist.");
        }


        //check player in the day and payment
        checkPlayer(timeslot);
        //check date in the future, start before end
        checkDateValidity(timeslot);
        //check timeslot duration
        checkTimeslotDuration(timeslot);
        //check if overlaps with another timeslot on the court
        checkTimeslotsOverlap(timeslot);
        //check if timeslot is in working hours
        checkWorkingHours(timeslot);
        //check start and end date
        Timeslot newTimeslot = Timeslot.builder()
                .courtId(timeslot.getCourtId())
                .playerId(timeslot.getPlayerId())
                .start(timeslot.getStart())
                .end(timeslot.getEnd())
                .build();
        timeslotRepository.save(newTimeslot);

        TimeslotReservationMessage timeslotReservationMessage = MessageFactory.createTimeslotReservationMessage(newTimeslot);
        messageService.sendMessageTimeslotTopic(timeslotReservationMessage);
    }

    public void checkPlayer(Timeslot timeslot){
        List<Timeslot> playersTimeslots = timeslotRepository.findAllByPlayerId(timeslot.getPlayerId());
        if (playersTimeslots.size() == 5){
            checkPayment(timeslot.getPlayerId());
        }


        playersTimeslots.stream().forEach(ts ->{
            if (ts.getStart().toLocalDate().isEqual(timeslot.getStart().toLocalDate())){
                throw new PlayerAlreadyReservedException
                        ("Player already has a reservation for chosen day.");

            }
        });
    }

    public void checkPayment(Long playerId){
        List<PaymentDTO> payments = paymentClient.getAll();
        if(!payments.stream().anyMatch(p->p.getPlayerId().equals(playerId))){
            throw new NotFoundException("Player must pay a 10 euros fee.");
        }
    }


    public void checkTimeslotDuration(Timeslot timeslot){
        long duration = getDateTimeDiff(timeslot.getStart(), timeslot.getEnd(), TimeUnit.MINUTES);

        if (duration < 30) {
            throw new InvalidTimeslotDateTimeException
                    (String.format("Minimum timeslot duration is 30 minutes, but duration is %d minutes.", duration));
        }else if (duration > 120){
            throw new InvalidTimeslotDateTimeException
                    (String.format("Maximum timeslot duration is 2 hours, but duration is %d hours.", TimeUnit.MINUTES.toHours(duration)));
        }
    }

    public void checkDateValidity(Timeslot timeslot){
        if (timeslot.getStart().isBefore(LocalDateTime.now())){
            throw new InvalidTimeslotDateTimeException
                    ("Timeslot must be in the future, but it's start is in the past.");

        }
        if (timeslot.getStart().isAfter(timeslot.getEnd())){
            throw new InvalidTimeslotDateTimeException
                    ("Chosen end time for timeslot is before the chosen start time.");

        }
    }

    public void checkTimeslotsOverlap(Timeslot timeslot){
        List<Timeslot> overlappingTimeslots = timeslotRepository.findAllTimeslotsByCourtAndTimeOverlap(timeslot.getCourtId(),
                timeslot.getStart(), timeslot.getEnd());
        if(!overlappingTimeslots.isEmpty()){
            throw new InvalidTimeslotDateTimeException
                    ("Timeslot is overlapping with another timeslot.");

        }
    }

    public void checkWorkingHours(Timeslot timeslot){
        DayOfWeek day = timeslot.getStart().getDayOfWeek();

        LocalTime startTimeWorkingDays = LocalTime.of(18, 0, 0);
        LocalTime endTimeWorkingDays = LocalTime.of(23, 0, 0);

        LocalTime startTimeWeekend = LocalTime.of(17, 0, 0);
        LocalTime endTimeWeekend = LocalTime.of(22, 0, 0);

        if(day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)){
            if (timeslot.getStart().toLocalTime().isBefore(startTimeWeekend) || timeslot.getEnd().toLocalTime().isAfter(endTimeWeekend)){
                throw new InvalidTimeslotDateTimeException
                        ("Timeslot chosen time not in weekend working hours.");
            }
        }else{
            if (timeslot.getStart().toLocalTime().isBefore(startTimeWorkingDays) || timeslot.getEnd().toLocalTime().isAfter(endTimeWorkingDays)){
                throw new InvalidTimeslotDateTimeException
                        ("Timeslot chosen time not in working days working hours.");
            }
        }
    }

    public static long getDateTimeDiff(LocalDateTime start, LocalDateTime end, TimeUnit timeUnit) {
        long duration = Duration.between(start, end).toMillis();
        return timeUnit.convert(duration,TimeUnit.MILLISECONDS);
    }


}
