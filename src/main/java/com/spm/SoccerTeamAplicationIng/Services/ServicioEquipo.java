package com.spm.SoccerTeamAplicationIng.Services;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import com.spm.SoccerTeamAplicationIng.Models.Equipo;

import java.util.List;

public interface ServicioEquipo {

    Equipo crearEquipo(EquipoInDto equipoInDto);

    List<Equipo> obtenerTodos();

    List<Equipo> obtenerTodosOrdenadosPorTamEstadio();

    Equipo obtenerEquipo(Long id);

    Equipo actualizarEquipo(Long id, EquipoInDto equipoAModificar) throws ApiUnprocessableEntity;

    boolean eliminarEquipo(Long id);
}
