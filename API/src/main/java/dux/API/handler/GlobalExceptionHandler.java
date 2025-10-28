package dux.API.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dux.API.equiposDeFutbol.exception.ConflictException;
import dux.API.equiposDeFutbol.exception.RecursoNoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    // 1. El tipo de retorno ahora es ResponseEntity<ErrorResponse>
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontradoException(
        RecursoNoEncontradoException ex) {

        // 2. Definimos el código de estado HTTP (404)
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        // 3. Creamos la instancia de ErrorResponse con los datos requeridos
        ErrorResponse errorResponse = new ErrorResponse(
            "Equipo no encontrado", // El mensaje fijo que solicitaste
            status.value()          // El valor entero del código HTTP (404)
        );
        
        // 4. Devolvemos el objeto ErrorResponse y el código 404
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(
        ConflictException ex) {

        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            status.value()
        );

        return new ResponseEntity<>(errorResponse, status);
    }
    
}
