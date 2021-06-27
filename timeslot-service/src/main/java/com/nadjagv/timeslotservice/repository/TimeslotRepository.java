package com.nadjagv.timeslotservice.repository;

import com.nadjagv.timeslotservice.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    List<Timeslot> findAllByPlayerId(Long playerId);

    @Query("select t from Timeslot t where t.courtId = :courtId and ((t.start >= :start and t.start<= :end) or (t.end>= :start and t.end<=:end))")
    List<Timeslot> findAllTimeslotsByCourtAndTimeOverlap(@Param("courtId") Long courtId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}
