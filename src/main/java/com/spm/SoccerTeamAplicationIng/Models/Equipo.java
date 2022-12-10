package com.spm.SoccerTeamAplicationIng.Models;

import com.sun.istack.NotNull;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("ID")
    private Long id;
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
