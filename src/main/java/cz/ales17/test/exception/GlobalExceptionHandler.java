package cz.ales17.test.exception;

import cz.ales17.test.entity.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException e) {
        ErrorObject eo = new ErrorObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date());
        return new ResponseEntity<>(eo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorObject> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        ErrorObject eo = new ErrorObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new Date());
        return new ResponseEntity<>(eo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorObject> handleAccessDeniedException(AccessDeniedException e) {
        ErrorObject eo = new ErrorObject(HttpStatus.FORBIDDEN.value(), e.getMessage(), new Date());
        return new ResponseEntity<>(eo, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorObject> handleTaskNotFoundException(TaskNotFoundException e) {
        ErrorObject eo = new ErrorObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date());
        return new ResponseEntity<>(eo, HttpStatus.NOT_FOUND);
    }
}
