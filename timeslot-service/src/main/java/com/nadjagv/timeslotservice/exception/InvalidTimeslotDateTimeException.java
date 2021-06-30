package com.nadjagv.timeslotservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidTimeslotDateTimeException extends RuntimeException {

    public InvalidTimeslotDateTimeException(String text) {
        super(text);
    }
}
