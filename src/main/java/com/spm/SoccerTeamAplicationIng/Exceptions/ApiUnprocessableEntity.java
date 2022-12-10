package com.spm.SoccerTeamAplicationIng.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ApiUnprocessableEntity extends Exception {

    public ApiUnprocessableEntity(String message) {
        super(message);
    }

}
