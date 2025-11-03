package dux.API.equiposDeFutbol.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dux.API.equiposDeFutbol.DTO.CreateEquipoDTO;
import dux.API.equiposDeFutbol.model.Equipo;
import dux.API.equiposDeFutbol.service.EquipoService;
import dux.API.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/equipos")
@Tag(name = "Equipos de Fútbol", description = "Endpoints para la gestión de equipos de fútbol")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @Operation(
        summary = "Obtiene la lista de todos los equipos",
        description = "Devuelve una lista de todos los equipos registrados en la base de datos.",
        responses = @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                               array = @ArraySchema(schema = @Schema(implementation = Equipo.class)))
        )
    )
    @GetMapping
    public List<Equipo> obtenerTodos() {
        return equipoService.findAll();
    }

    @Operation(
        summary = "Obtiene un equipo por su ID",
        description = "Busca un equipo específico utilizando su identificador único.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))
            ),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @GetMapping("/{id}")
    public Equipo obtenerPorId(@PathVariable Long id) {
        return equipoService.findById(id);
    }

    @Operation(
        summary = "Busca un equipo por nombre",
        description = "Busca y devuelve un equipo específico por su nombre exacto.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))
            ),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @GetMapping("/buscar")
    public Equipo getMethodName(@RequestParam String nombre) {
        return equipoService.findByNombre(nombre);
    }
    

    @Operation(
        summary = "Crea un nuevo equipo",
        description = "Registra un equipo validando que los datos sean correctos (campos obligatorios) y que el nombre sea único.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))
            ),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "409",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PostMapping
    public ResponseEntity<Equipo> create(@Valid @RequestBody CreateEquipoDTO equipoDTO) {
        Equipo nuevoEquipo = equipoService.save(equipoDTO);
        return new ResponseEntity<>(nuevoEquipo, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Actualiza un equipo existente",
        description = "Actualiza completamente un equipo buscando por su ID. Utiliza el mismo DTO de creación para la validación.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Devuelve la información actualizada del equipo.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Equipo.class))
            ),
            @ApiResponse(
                responseCode = "400",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Si el equipo no existe.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PutMapping("/{id}")
    public Equipo update(@PathVariable Long id, @Valid @RequestBody CreateEquipoDTO body) { // valido con el mismo DTO porque actualizo el equipo completo
        return equipoService.update(id, body);
    }

    @Operation(
        summary = "Elimina un equipo por ID",
        description = "Elimina permanentemente un equipo específico de la base de datos.",
        responses = {
            @ApiResponse(
                responseCode = "204"
            ),
            @ApiResponse(
                responseCode = "404",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
