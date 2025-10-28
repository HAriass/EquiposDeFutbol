package dux.API.equiposDeFutbol.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    //recibo el mensaje de error en el constructor
    public RecursoNoEncontradoException(String mensaje) {
        //llamo al constructor de la clase padre RuntimeException para guardar el mensaje
        super(mensaje);
    }
    
}
