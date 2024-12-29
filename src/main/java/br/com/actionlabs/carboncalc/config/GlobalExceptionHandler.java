package br.com.actionlabs.carboncalc.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler for exceptions of type IllegalArgumentException, which means the supplied data is not valid
     * @param ex The exception
     * @return A response entity with the exception message and a BAD_REQUEST status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    /**
     * Handler for exceptions in general, which means an internal error has occurred
     * @return A response entity <b>without</b> the exception message, but a default message to contact administrator
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException() {
        return new ResponseEntity<>("An internal error has occurred. Please contact the administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
