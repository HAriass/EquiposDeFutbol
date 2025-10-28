package dux.API.equiposDeFutbol.service;

import java.util.List;

import dux.API.equiposDeFutbol.DTO.CreateEquipoDTO;
import dux.API.equiposDeFutbol.model.Equipo;

public interface EquipoService {
    List<Equipo> findAll();
    Equipo findById(Long id);
    Equipo save(CreateEquipoDTO equipoDTO);
}
