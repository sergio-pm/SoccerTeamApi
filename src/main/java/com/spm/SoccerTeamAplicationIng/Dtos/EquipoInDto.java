package com.spm.SoccerTeamAplicationIng.Dtos;

import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import com.sun.istack.NotNull;
import jdk.jfr.Name;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class EquipoInDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @NotBlank
    @Name("Nombre")
    private String nombre;
    @Name("Ciudad")
    private String ciudad;
    @Name("Propietario")
    private String propietario;
    @Name("Capacidad del estadio")
    private int capacidadEstadio;
    @Name("División")
    private int division;
    @Name("Competición")
    private String competicion;
    @Name("Número de jugadores")
    private int numJugadores;
    @Name("Fecha de creación")
    private String fechaCreacion;
}
