package org.yesidl12.centinelaseguraltda.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ServicioConflictException extends RuntimeException {
    public ServicioConflictException(String message) {
        super(message);
    }
}
