package com.nadjagv.timeslotservice.repository;

import com.nadjagv.timeslotservice.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

}
