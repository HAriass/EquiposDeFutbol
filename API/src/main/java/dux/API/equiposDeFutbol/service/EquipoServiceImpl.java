package dux.API.equiposDeFutbol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dux.API.equiposDeFutbol.DTO.CreateEquipoDTO;
import dux.API.equiposDeFutbol.exception.ConflictException;
import dux.API.equiposDeFutbol.exception.RecursoNoEncontradoException;
import dux.API.equiposDeFutbol.model.Equipo;
import dux.API.equiposDeFutbol.repository.EquipoRepository;

@Service
public class EquipoServiceImpl implements EquipoService {
    private final EquipoRepository equipoRepository;

    public EquipoServiceImpl(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> findAll() {
        return this.equipoRepository.findAll();
    }

    public Equipo findById(Long id) {
        return this.equipoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Equipo no encontrado con id: " + id));
    }

    public Equipo save(CreateEquipoDTO equipoDTO) {

        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setLiga(equipoDTO.getLiga());
        equipo.setPais(equipoDTO.getPais());

        if (this.equipoRepository.existsByNombre(equipo.getNombre())) {
            throw new ConflictException("Ya existe un equipo con el nombre: " + equipo.getNombre());
        }
        
        return equipoRepository.save(equipo);
    }
    
}
