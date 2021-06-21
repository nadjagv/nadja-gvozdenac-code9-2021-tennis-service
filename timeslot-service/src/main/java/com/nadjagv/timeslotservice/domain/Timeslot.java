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

    @Column
    LocalDateTime start;
    @Column
    LocalDateTime end;
    @Column
    private Long courtId;
    @Column
    private Long playerId;
}
