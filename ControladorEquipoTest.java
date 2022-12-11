package com.spm.SoccerTeamAplicationIng.Controllers;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import com.spm.SoccerTeamAplicationIng.Persistence.RepositorioEquipo;
import com.spm.SoccerTeamAplicationIng.Services.ServicioEquipo;
import com.spm.SoccerTeamAplicationIng.Validator.EquipoValidator;
import com.spm.SoccerTeamAplicationIng.Validator.EquipoValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ControladorEquipoTest {

    @Autowired
    private static MockMvc mockMvc;

    @Mock
    private ServicioEquipo servicioEquipo;

    @Mock
    private EquipoValidator equipoValidator;

    @Mock
    private EquipoValidatorImpl validator;

    @Mock
    private RepositorioEquipo repositorioEquipo;

    @InjectMocks
    private ControladorEquipo controladorEquipo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crearEquipo() throws ApiUnprocessableEntity, ParseException {
        EquipoInDto equipoInDto = new EquipoInDto();
        equipoInDto.setNombre("Yeles Club de futbol");
        equipoInDto.setFechaCreacion("1986-10-01");
        equipoInDto.setCiudad("Yeles");
        equipoInDto.setNumJugadores(22);
        equipoInDto.setPropietario("Pedro Fernandez");
        equipoInDto.setCompeticion("Futbol");
        equipoInDto.setDivision(3);
        equipoInDto.setCapacidadEstadio(3500);

        this.equipoValidator.validator(equipoInDto);
        ResponseEntity<EquipoInDto> httpResponse = controladorEquipo.crearEquipo(equipoInDto);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void crearEquipoWithException() throws ApiUnprocessableEntity, ParseException {

        ResponseEntity<EquipoInDto> httpResponse = controladorEquipo.crearEquipo(null);
        assertEquals(httpResponse.getStatusCode(), HttpStatus.CREATED);
    }


    @Test
    public void obtenerTodosOK() {

        Equipo equipo1 = new Equipo(1L,"Yeles Club de futbol","Yeles",
                "Pedro Fernandez",3500,3,"Futbol",
                22,"1986-10-01");

        when(repositorioEquipo.findAll()).thenReturn(Arrays.asList(equipo1));
        assertNotNull(controladorEquipo.obtenerTodos());

    }

    @Test
    public void obtenerTodosOrdenadosOK() {
        Equipo equipo1 = new Equipo(1L,"Yeles Club de futbol","Yeles",
                "Pedro Fernandez",3500,3,"Futbol",
                22,"1986-10-01");

        when(repositorioEquipo.findAll(any(Sort.class))).thenReturn(Arrays.asList(equipo1));
        assertNotNull(controladorEquipo.obtenerTodos());
    }

    @Test
    public void obtenerTodosKo() {
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();

        doReturn(Arrays.asList(equipo1, equipo2)).when(this.repositorioEquipo).findAll();

        ResponseEntity equipoList = this.controladorEquipo.obtenerTodos();


        assertEquals(equipoList.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void obtenerTodosOrdenadosKo() {
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();

        doReturn(Arrays.asList(equipo1, equipo2)).when(this.repositorioEquipo).findAll();

        ResponseEntity equipoList = this.controladorEquipo.obtenerTodosOrdenadosPorTamEstadio();


        assertEquals(equipoList.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void obtenerEquipo() {

        Equipo equipo = new Equipo();
        equipo.setId(1L);
        equipo.setNombre("Yeles Club de futbol");
        equipo.setFechaCreacion("1986-10-01");
        equipo.setCiudad("Yeles");
        equipo.setNumJugadores(22);
        equipo.setPropietario("Pedro Fernandez");
        equipo.setCompeticion("Futbol");
        equipo.setDivision(3);
        equipo.setCapacidadEstadio(3500);

        doReturn(Optional.of(equipo)).when(repositorioEquipo).findById(1l);


        Optional<ResponseEntity> equipoRetornado = Optional.ofNullable(controladorEquipo.obtenerEquipo(1l));


        Assertions.assertTrue(equipoRetornado.isPresent(), "Equipo encontrado");
        Assertions.assertSame(equipoRetornado.get().getStatusCode(),
                HttpStatus.OK, "El HttpStatus retornado es el esperado");
    }

    @Test
    public void obtenerEquipoKo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            Equipo equipo = new Equipo();
            equipo.setId(89L);

            Mockito.doThrow(new NullPointerException()).when(servicioEquipo).obtenerEquipo(equipo.getId());

            mockMvc.perform(get("/obtener/" + equipo.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        });

    }

    @Test
    public void eliminarEquipoOk() throws Exception {

        Equipo equipo = new Equipo();
        equipo.setId(1L);
        equipo.setNombre("Yeles Club de futbol");
        equipo.setFechaCreacion("1986-10-01");
        equipo.setCiudad("Yeles");
        equipo.setNumJugadores(22);
        equipo.setPropietario("Pedro Fernandez");
        equipo.setCompeticion("Futbol");
        equipo.setDivision(3);
        equipo.setCapacidadEstadio(3500);

        doReturn(Optional.of(equipo)).when(repositorioEquipo).findById(1L);


        Optional<ResponseEntity> equipoRetornado = Optional.ofNullable(controladorEquipo.eliminarEquipo(1L));

        Assertions.assertSame(equipoRetornado.get().getStatusCode(),
                HttpStatus.NOT_FOUND, "Equipo borrado");

    }
    @Test
    public void eliminarEquipoKo() throws Exception {

        Assertions.assertThrows(NullPointerException.class, () -> {
        Equipo equipo = new Equipo();
        equipo.setId(89L);

        Mockito.doThrow(new NullPointerException()).when(servicioEquipo).eliminarEquipo(equipo.getId());

        mockMvc.perform(delete("/eliminar/" + equipo.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        });
    }

}
