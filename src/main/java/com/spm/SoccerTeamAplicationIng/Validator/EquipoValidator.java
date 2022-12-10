package com.spm.SoccerTeamAplicationIng.Validator;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface EquipoValidator {

    void validator(EquipoInDto equipoInDto) throws ApiUnprocessableEntity, ParseException;
}
