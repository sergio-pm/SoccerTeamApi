package com.spm.SoccerTeamAplicationIng.Controllers;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import com.spm.SoccerTeamAplicationIng.Services.ServicioEquipo;
import com.spm.SoccerTeamAplicationIng.Validator.EquipoValidatorImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/equipos")
public class ControladorEquipo {

    @Autowired
    private final ServicioEquipo servicioEquipo;

    @Autowired
    private final EquipoValidatorImpl equipoValidator;

    @PostMapping("/crear")
    public ResponseEntity<EquipoInDto> crearEquipo(@RequestBody EquipoInDto equipoInDto)
            throws ApiUnprocessableEntity, ParseException {

        this.equipoValidator.validator(equipoInDto);
        return new ResponseEntity(servicioEquipo.crearEquipo(equipoInDto), HttpStatus.CREATED);
    }

    @GetMapping("/obtener/todos")
    public ResponseEntity obtenerTodos() {
        Map<String,Object> response = new HashMap<>();
        boolean respuesta = servicioEquipo.obtenerTodos().isEmpty();
        if(respuesta){
            response.put("Mensaje", "La lista esta vacia");
            return new ResponseEntity(response, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity(servicioEquipo.obtenerTodos(), HttpStatus.OK);
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity obtenerEquipo(@PathVariable("id") Long idEquipo) {
        Map<String, Object> response = new HashMap<>();
        try {
            return new ResponseEntity(servicioEquipo.obtenerEquipo(idEquipo), HttpStatus.OK);
        } catch (RuntimeException ex) {

            response.put("Mensaje", "No se encuentra el equipo con el id "
                    .concat(idEquipo.toString()));
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/modificar/{id}")
    public ResponseEntity actualizarEquipo(@PathVariable("id") Long idEquipo, @RequestBody EquipoInDto equipoInDto)
            throws ApiUnprocessableEntity, ParseException {
        this.equipoValidator.validator(equipoInDto);
        return new ResponseEntity(servicioEquipo.actualizarEquipo(idEquipo, equipoInDto), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarEquipo(@PathVariable("id") Long idEquipo) throws Exception {
       boolean respuesta = servicioEquipo.eliminarEquipo(idEquipo);
        Map<String,Object> response = new HashMap<>();
        if (respuesta) {

            response.put("Mensaje","Se ha eliminado el equipo con el id "
                    .concat(idEquipo.toString().concat(" con exito")));
            return new ResponseEntity(response, HttpStatus.OK);
        } else {
            response.put("Mensaje", "No se encuentra el equipo con el id "
                    .concat(idEquipo.toString()));
            return new ResponseEntity(response,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "obtener/todos/ordenados")
    public ResponseEntity obtenerTodosOrdenadosPorTamEstadio() {
        Map<String, Object> response = new HashMap<>();
        boolean respuesta = servicioEquipo.obtenerTodosOrdenadosPorTamEstadio().isEmpty();
        if (respuesta) {
            response.put("Mensaje", "La lista esta vacia");
            return new ResponseEntity(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(servicioEquipo.obtenerTodosOrdenadosPorTamEstadio(), HttpStatus.OK);
        }
    }
}