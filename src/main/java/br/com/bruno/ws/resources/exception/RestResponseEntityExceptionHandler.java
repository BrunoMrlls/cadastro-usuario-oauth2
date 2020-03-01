package br.com.bruno.ws.resources.exception;

import br.com.bruno.ws.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e
        , HttpServletRequest request) {

        final long time = System.currentTimeMillis();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        StandartError err = new StandartError( time, httpStatus.value(), "Não encontrado", e.getMessage(), request.getRequestURI() );
        return ResponseEntity.status( httpStatus ).body( err );
    }
}
