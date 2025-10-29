package dux.API.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Estructura de la respuesta de error para códigos HTTP")
public class ErrorResponse {

    @Schema(description = "Mensaje de error. Puede ser genérico (400) o específico (409, 404).", example = "La solicitud es invalida")
    private String mensaje;
    @Schema(description = "Código de estado HTTP asociado al error.", example = "400")
    private int codigo;
}
