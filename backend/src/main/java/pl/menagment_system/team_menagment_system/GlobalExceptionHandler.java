package pl.menagment_system.team_menagment_system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * A global exception handler to manage exceptions across the entire application
 * through a centralized mechanism, improving maintainability and readability of the code.
 *
 * This class uses the @ControllerAdvice annotation to define common exception handling for all
 * the controllers in the application.
 *
 * The handleValidationExceptions method is responsible for processing MethodArgumentNotValidException instances
 * and returning a response entity containing a map of validation error messages for invalid fields.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}