package com.nadjagv.adminloginservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "admins")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    private String email;

    @Column
    private String password;
}
