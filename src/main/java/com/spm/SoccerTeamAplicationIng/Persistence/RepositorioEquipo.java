package com.spm.SoccerTeamAplicationIng.Persistence;

import com.spm.SoccerTeamAplicationIng.Models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEquipo extends JpaRepository<Equipo,Long> {
}
