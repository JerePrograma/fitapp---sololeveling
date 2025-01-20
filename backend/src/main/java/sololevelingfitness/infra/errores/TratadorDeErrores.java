package sololevelingfitness.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DatosErrorGeneral> tratarError404(EntityNotFoundException e) {
        var error = new DatosErrorGeneral(
                "Recurso no encontrado",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DatosErrorGeneral> tratarError403(AccessDeniedException e) {
        var error = new DatosErrorGeneral(
                "Acceso denegado",
                "No tienes permisos para acceder a este recurso: " + e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DatosErrorGeneral> tratarErrorGenerico(RuntimeException e) {
        var error = new DatosErrorGeneral(
                "Error interno del servidor",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DatosErrorGeneral> manejarErrorDeArgumentoInvalido(IllegalArgumentException e) {
        var error = new DatosErrorGeneral(
                "Argumento invalido",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(error);
    }


    // Clase para errores de validacion de campos especificos
    private record DatosErrorValidacion(String campo, String mensaje) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    // Clase para errores generales
    private record DatosErrorGeneral(String tipo, String detalle, LocalDateTime timestamp) {}

}
