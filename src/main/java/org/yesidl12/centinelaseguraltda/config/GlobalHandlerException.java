package org.yesidl12.centinelaseguraltda.config;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yesidl12.centinelaseguraltda.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yesidl12.centinelaseguraltda.exception.ServicioConflictException;
import org.yesidl12.centinelaseguraltda.exception.UsuarioConflictException;

@RestControllerAdvice
public class GlobalHandlerException {

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorResponse> handleException(Exception e) {
    //     return new ResponseEntity<>(
    //         new ErrorResponse(
    //             java.time.LocalDateTime.now(),
    //             HttpStatus.INTERNAL_SERVER_ERROR.value(),
    //             e.getMessage()
    //         ),
    //         HttpStatus.INTERNAL_SERVER_ERROR
    //     );
    // }

    @ExceptionHandler(UsuarioConflictException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioConflictException(UsuarioConflictException e) {
        return new ResponseEntity<>(
            new ErrorResponse(
                java.time.LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage()
            ),
            HttpStatus.CONFLICT
        );
    }   

    @ExceptionHandler(ServicioConflictException.class)
    public ResponseEntity<ErrorResponse> handleServicioConflictException(ServicioConflictException e) {
        return new ResponseEntity<>(
            new ErrorResponse(
                java.time.LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage()
            ),
            HttpStatus.CONFLICT
        );
    }
}
