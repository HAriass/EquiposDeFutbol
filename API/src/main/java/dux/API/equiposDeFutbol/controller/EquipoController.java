package dux.API.equiposDeFutbol.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dux.API.equiposDeFutbol.DTO.CreateEquipoDTO;
import dux.API.equiposDeFutbol.model.Equipo;
import dux.API.equiposDeFutbol.service.EquipoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public List<Equipo> obtenerTodos() {
        return equipoService.findAll();
    }

    @GetMapping("/{id}")
    public Equipo obtenerPorId(@PathVariable Long id) {
        return equipoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Equipo> create(@RequestBody CreateEquipoDTO equipoDTO) {
        Equipo nuevoEquipo = equipoService.save(equipoDTO);
        return new ResponseEntity<>(nuevoEquipo, HttpStatus.CREATED);
    }
    
    
}
