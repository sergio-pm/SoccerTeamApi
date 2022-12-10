package com.spm.SoccerTeamAplicationIng.Validator;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EquipoValidatorImpl implements EquipoValidator {
    @Override
    public void validator(EquipoInDto equipoInDto) throws ApiUnprocessableEntity, ParseException {

        validarNombre(equipoInDto);
        validarCapacidadEstadio(equipoInDto);
        validarNumJugadores(equipoInDto);
        validarDivision(equipoInDto);
        validarFechaCreacion(equipoInDto);
        validarDivisionYCapacidadEstadio(equipoInDto);
    }

    public void validarNombre(EquipoInDto equipoInDto) throws ApiUnprocessableEntity {
        if (equipoInDto.getNombre() == null || equipoInDto.getNombre().isEmpty()) {
            this.message("El nombre es obligatorio");
        }
    }
    public void validarCapacidadEstadio(EquipoInDto equipoInDto) throws ApiUnprocessableEntity {
        if (equipoInDto.getCapacidadEstadio() < 1) {
            this.message("El valor de la capacidad del estadio debe ser positivo");
        }
    }

    public void validarNumJugadores(EquipoInDto equipoInDto) throws ApiUnprocessableEntity {
        if (equipoInDto.getNumJugadores() < 1) {
            message("El valor de numero de jugadores debe ser positivo");
        }
    }

    public void validarDivision(EquipoInDto equipoInDto) throws ApiUnprocessableEntity {
        if (equipoInDto.getDivision() !=1 && equipoInDto.getDivision() !=2 && equipoInDto.getDivision() !=3) {
            message("El valor de división solo puede ser uno, dos o tres");
        }
    }

    public void validarFechaCreacion(EquipoInDto equipoInDto) throws ApiUnprocessableEntity, ParseException {
        Date fechaActual = new Date(System.currentTimeMillis());
        String fechaInicio = equipoInDto.getFechaCreacion();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicioDate = date.parse(fechaInicio);

        if (fechaInicioDate.after(fechaActual)) {

            message("La fecha de creación debe ser anterior a hoy");
        }
    }
        public void validarDivisionYCapacidadEstadio(EquipoInDto equipoInDto) throws ApiUnprocessableEntity {
            if (equipoInDto.getDivision()==1 && equipoInDto.getCapacidadEstadio()<50001){
                message("La capacidad del estadio de division 1 debe ser mayor de 50000");
            } else if (equipoInDto.getDivision()==2 && equipoInDto.getCapacidadEstadio()<10001) {
                message("La capacidad del estadio de division 2 debe ser mayor de 10000");
            } else if (equipoInDto.getDivision()==3 && equipoInDto.getCapacidadEstadio()<3001) {
                message("La capacidad del estadio de division 3 debe ser mayor de 3000");}
        }

    public void message(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }
}
