package com.nadjagv.timeslotservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlayerAlreadyReservedException extends RuntimeException {

    public PlayerAlreadyReservedException(String text) {
        super(text);
    }
}
