package dux.API.equiposDeFutbol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dux.API.equiposDeFutbol.model.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    boolean existsByNombre(String nombre);
}
