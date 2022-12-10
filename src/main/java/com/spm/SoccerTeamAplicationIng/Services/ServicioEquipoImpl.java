package com.spm.SoccerTeamAplicationIng.Services;

import com.spm.SoccerTeamAplicationIng.Dtos.EquipoInDto;
import com.spm.SoccerTeamAplicationIng.Exceptions.ApiUnprocessableEntity;
import com.spm.SoccerTeamAplicationIng.Mappers.EquipoInDtoToEquipo;
import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import com.spm.SoccerTeamAplicationIng.Persistence.RepositorioEquipo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioEquipoImpl implements ServicioEquipo{

    private final EquipoInDtoToEquipo mapper;
    private final RepositorioEquipo repositorioEquipo;

    @Override
    public Equipo crearEquipo(EquipoInDto equipoInDto) {
        Equipo equipo = mapper.map(equipoInDto);
        return repositorioEquipo.save(equipo);
    }

    @Override
    public List<Equipo> obtenerTodos() {
        return repositorioEquipo.findAll();
    }

    @Override
    public List<Equipo> obtenerTodosOrdenadosPorTamEstadio() {
        return repositorioEquipo.findAll(Sort.by("capacidadEstadio").descending());
    }

    @Override
    public Equipo obtenerEquipo(Long idEquipo) {
        return repositorioEquipo.findById(idEquipo).orElseThrow(() -> {
            throw new RuntimeException();
        });
    }

    @Override
    public Equipo actualizarEquipo(Long idEquipo, EquipoInDto equipoAModificar) throws ApiUnprocessableEntity {
        Optional<Equipo> optionalEquipoBuscado= this.repositorioEquipo.findById(idEquipo);
        if (optionalEquipoBuscado.isEmpty()){
            throw new ApiUnprocessableEntity("No se encuentra equipo con ese ID");
        }else{
        Equipo equipoBuscado=this.repositorioEquipo.findById(idEquipo).get();
        equipoBuscado.setCiudad(equipoAModificar.getCiudad());
        equipoBuscado.setCompeticion(equipoAModificar.getCompeticion());
        equipoBuscado.setNombre(equipoAModificar.getNombre());
        equipoBuscado.setDivision(equipoAModificar.getDivision());
        equipoBuscado.setPropietario(equipoAModificar.getPropietario());
        equipoBuscado.setCapacidadEstadio(equipoAModificar.getCapacidadEstadio());
        equipoBuscado.setNumJugadores(equipoAModificar.getNumJugadores());
        return repositorioEquipo.save(equipoBuscado);
        }
    }

    @Override
    public boolean eliminarEquipo(Long idEquipo) {
        try {
            repositorioEquipo.deleteById(idEquipo);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
