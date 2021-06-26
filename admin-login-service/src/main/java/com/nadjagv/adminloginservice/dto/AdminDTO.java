package com.nadjagv.adminloginservice.dto;

import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;

@Value
@Builder
public class AdminDTO {
    Long id;

    String email;

    String password;
}
