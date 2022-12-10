package com.spm.SoccerTeamAplicationIng.Services;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import com.spm.SoccerTeamAplicationIng.Mappers.EquipoInDtoToEquipo;
import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import com.spm.SoccerTeamAplicationIng.Persistence.RepositorioEquipo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ServicioEquipoImplTest {

    @Mock
    private EquipoInDtoToEquipo mapper;

    @Mock
    private RepositorioEquipo repositorioEquipo;

    @InjectMocks
    private ServicioEquipoImpl servicioEquipo;

    private Equipo equipo;

    private EquipoInDto equipoInDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        equipo = new Equipo();
        equipo.setId(1L);
        equipo.setNombre("Yeles Club de futbol");
        equipo.setFechaCreacion("1986-10-01");
        equipo.setCiudad("Yeles");
        equipo.setNumJugadores(22);
        equipo.setPropietario("Pedro Fernandez");
        equipo.setCompeticion("Futbol");
        equipo.setDivision(3);
        equipo.setCapacidadEstadio(3500);

        equipoInDto = new EquipoInDto();

        equipoInDto.setNombre("Yeles Club de futbol");
        equipoInDto.setFechaCreacion("1986-10-01");
        equipoInDto.setCiudad("Yeles");
        equipoInDto.setNumJugadores(22);
        equipoInDto.setPropietario("Pedro Fernandez");
        equipoInDto.setCompeticion("Futbol");
        equipoInDto.setDivision(3);
        equipoInDto.setCapacidadEstadio(3500);
    }

    @Test
    void crearEquipo() {

        when(repositorioEquipo.save(any(Equipo.class))).thenReturn(equipo);
        when(mapper.map(equipoInDto)).thenReturn(equipo);
        assertNotNull(servicioEquipo.crearEquipo(equipoInDto));
    }

    @Test
    void obtenerTodos() {
        when(repositorioEquipo.findAll()).thenReturn(Arrays.asList(equipo));
        assertNotNull(servicioEquipo.obtenerTodos());
    }

    @Test
    void obtenerTodosOrdenadosPorTamEstadio() {
        when(repositorioEquipo.findAll(any(Sort.class))).thenReturn(Arrays.asList(equipo));
        assertNotNull(servicioEquipo.obtenerTodosOrdenadosPorTamEstadio());
    }

    @Test
    void obtenerEquipoOK() {
        when(repositorioEquipo.findById(equipo.getId())).thenReturn(Optional.ofNullable(equipo));
        assertNotNull(servicioEquipo.obtenerEquipo(equipo.getId()));
    }

    @Test
    void obtenerEquipoKO() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Equipo equipo = servicioEquipo.obtenerEquipo(0L);
        });
    }

    @Test
    void actualizarEquipoKO() throws ApiUnprocessableEntity {

        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setNombre("Leganes");
            Equipo equipo = servicioEquipo.actualizarEquipo(1L, equipoInDto);
        });
    }

    @Test
    void eliminarEquipoOK() {
        when(repositorioEquipo.findById(equipo.getId())).thenReturn(Optional.ofNullable(equipo));
        assertTrue(servicioEquipo.eliminarEquipo(equipo.getId()));
    }

    @Test
    void eliminarEquipoKO() {

        Assertions.assertThrows(MockitoException.class, () -> {
            ApiUnprocessableEntity ex = new ApiUnprocessableEntity("No se encuentra");
            when(servicioEquipo.eliminarEquipo(null)).thenThrow(ex);
        });
    }
}