package com.spm.SoccerTeamAplicationIng.Mappers;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import org.springframework.stereotype.Component;

@Component
public class EquipoInDtoToEquipo implements Imapper<EquipoInDto, Equipo> {

    @Override
    public Equipo map(EquipoInDto in) {
        Equipo equipo= new Equipo();
        equipo.setCiudad(in.getCiudad());
        equipo.setNombre(in.getNombre());
        equipo.setCompeticion(in.getCompeticion());
        equipo.setDivision(in.getDivision());
        equipo.setPropietario(in.getPropietario());
        equipo.setCapacidadEstadio(in.getCapacidadEstadio());
        equipo.setNumJugadores(in.getNumJugadores());
        equipo.setFechaCreacion(in.getFechaCreacion());
        return equipo;
    }
}
