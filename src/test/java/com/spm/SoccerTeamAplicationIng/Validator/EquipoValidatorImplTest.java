package com.spm.SoccerTeamAplicationIng.Validator;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import com.spm.SoccerTeamAplicationIng.Mappers.EquipoInDtoToEquipo;
import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import com.spm.SoccerTeamAplicationIng.Persistence.RepositorioEquipo;
import com.spm.SoccerTeamAplicationIng.Services.ServicioEquipoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EquipoValidatorImplTest {

    @Mock
    private EquipoInDtoToEquipo mapper;

    @Mock
    private RepositorioEquipo repositorioEquipo;

    @Mock
    private ServicioEquipoImpl servicioEquipo;


    private Equipo equipo;

    private EquipoInDto equipoInDto;

    @InjectMocks
    private EquipoValidatorImpl equipoValidator;


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
    void validarNombreok() throws ApiUnprocessableEntity {

        equipoValidator.validarNombre(equipoInDto);
        assertNotNull(equipoInDto.getNombre());

    }

    @Test
    void validarNombreKO() throws ApiUnprocessableEntity {

        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setNombre(null);
            equipoValidator.validarNombre(equipoInDto);
        });
    }

    @Test
    void validarCapacidadEstadioOK() throws ApiUnprocessableEntity {

        boolean validar= false;
        equipoValidator.validarCapacidadEstadio(equipoInDto);

        if (equipoInDto.getCapacidadEstadio() > 0){
            validar=true;
        }
        assertTrue(validar);
    }

    @Test
    void validarCapacidadEstadioKO() throws ApiUnprocessableEntity {

        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setCapacidadEstadio(-1);
            equipoValidator.validarCapacidadEstadio(equipoInDto);
        });
    }

    @Test
    void validarNumJugadoresOK() throws ApiUnprocessableEntity {
        boolean validar= false;
        equipoValidator.validarNumJugadores(equipoInDto);

        if (equipoInDto.getNumJugadores() > 0){
            validar=true;
        }
        assertTrue(validar);
    }

    @Test
    void validarNumJugadoresKO() {

        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setNumJugadores(-1);
            equipoValidator.validarNumJugadores(equipoInDto);
        });
    }

    @Test
    void validarDivisionOK() throws ApiUnprocessableEntity {
        boolean validar= false;
        equipoValidator.validarDivision(equipoInDto);
        if (equipoInDto.getDivision() ==1 || equipoInDto.getDivision() ==2 || equipoInDto.getDivision() ==3) {
            validar=true;
        }assertTrue(validar);
    }

    @Test
    void validarDivisionKO() {
        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setDivision(5);
            equipoValidator.validarDivision(equipoInDto);
        });
    }

    @Test
    void validarFechaCreacionOK() throws ApiUnprocessableEntity, ParseException {
        boolean validar= false;
        equipoValidator.validarFechaCreacion(equipoInDto);
        Date fechaActual = new Date(System.currentTimeMillis());
        String fechaInicio = equipoInDto.getFechaCreacion();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicioDate = date.parse(fechaInicio);

        if (fechaInicioDate.before(fechaActual)) {
            validar = true;
        }
        assertTrue(validar);
    }

    @Test
    void validarFechaCreacionKO() {
        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setFechaCreacion("2023-10-06");
            equipoValidator.validarFechaCreacion(equipoInDto);
        });
    }

    @Test
    void validarDivisionYCapacidadEstadioOK() throws ApiUnprocessableEntity {
        boolean validar= false;
        equipoValidator.validarDivisionYCapacidadEstadio(equipoInDto);

        if (equipoInDto.getDivision()==1 || equipoInDto.getCapacidadEstadio()<50001){
            validar = true;
        } else if (equipoInDto.getDivision()==2 || equipoInDto.getCapacidadEstadio()<10001) {
            validar = true;
        } else if (equipoInDto.getDivision()==3 || equipoInDto.getCapacidadEstadio()<3001) {
            validar = true;}
        assertTrue(validar);
    }

    @Test
    void validarDivisionYCapacidadEstadioKO() {

        Assertions.assertThrows(ApiUnprocessableEntity.class, () -> {
            equipoInDto.setDivision(1);
            equipoInDto.setCapacidadEstadio(100);
            equipoValidator.validarDivisionYCapacidadEstadio(equipoInDto);
        });
    }
}