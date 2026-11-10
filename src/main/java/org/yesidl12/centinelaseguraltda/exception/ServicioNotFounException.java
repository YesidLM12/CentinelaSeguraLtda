package org.yesidl12.centinelaseguraltda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServicioNotFounException extends RuntimeException {

    public ServicioNotFounException(String message) {
        super(message);
    }

}
