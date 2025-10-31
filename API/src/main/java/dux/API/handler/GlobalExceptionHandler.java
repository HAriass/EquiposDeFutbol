package dux.API.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dux.API.equiposDeFutbol.exception.ConflictException;
import dux.API.equiposDeFutbol.exception.RecursoNoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontradoException(
        RecursoNoEncontradoException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(), 
            status.value()          
        );
        
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
    
    //Maneja errores de validacion de datos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
        MethodArgumentNotValidException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        ErrorResponse errorResponse = new ErrorResponse(
            "La solicitud es invalida", 
            status.value()              
        );
        
        return new ResponseEntity<>(errorResponse, status);
    }

    //Maneja errores de JSON mal formateado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex) {

        // Si Spring no puede leer el JSON, significa que la solicitud es incorrecta.
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        ErrorResponse errorResponse = new ErrorResponse(
            "La solicitud es invalida", // Mensaje fijo para el 400
            status.value()              // Código 400
        );
        
        return new ResponseEntity<>(errorResponse, status);
    }

}
