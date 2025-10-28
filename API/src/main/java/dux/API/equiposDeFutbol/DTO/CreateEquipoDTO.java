package dux.API.equiposDeFutbol.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEquipoDTO {
    
    private String nombre;
    private String liga;
    private String pais;
}
