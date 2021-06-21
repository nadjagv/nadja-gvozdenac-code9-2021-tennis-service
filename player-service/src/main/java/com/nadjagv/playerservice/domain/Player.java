package com.nadjagv.playerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "players")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private LocalDate dateOfBirth;

    @Column
    private Integer totalTimeslots;

    @Column
    private PaymentType paymentType;
    @Column
    private Boolean paid;

}

