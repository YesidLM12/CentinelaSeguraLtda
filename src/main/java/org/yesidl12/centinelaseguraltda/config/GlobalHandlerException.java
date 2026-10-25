package org.yesidl12.centinelaseguraltda.config;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yesidl12.centinelaseguraltda.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(
            new ErrorResponse(
                java.time.LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioConflictException(RuntimeException e) {
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
