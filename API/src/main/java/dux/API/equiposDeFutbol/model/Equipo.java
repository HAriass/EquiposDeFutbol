package dux.API.equiposDeFutbol.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "equipos")
@Schema(description = "Representación completa de un equipo de fútbol en el sistema")
public class Equipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único generado por el sistema", example = "26", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Nombre del equipo de fútbol", example = "FC Barcelona")
    private String nombre;
    @Schema(description = "Liga a la que pertenece el equipo", example = "La Liga")
    private String liga;
    @Schema(description = "País de origen del equipo", example = "España")
    private String pais;
    
}
