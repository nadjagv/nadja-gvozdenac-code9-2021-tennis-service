package com.nadjagv.timeslotservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "timeslots")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Timeslot {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime end;
    @Column(name = "court_id")
    private Long courtId;
    @Column(name = "player_id")
    private Long playerId;
}
