package com.nadjagv.courtservice.repository;

import com.nadjagv.courtservice.domain.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
}
