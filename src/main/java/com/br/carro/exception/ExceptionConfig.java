package com.br.carro.exception;

import java.io.Serializable;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<?> errorNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> errorBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorException(ex.getMessage(), new Date()));
    }
    
    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<?> objectNotFoundException(ObjectNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> accessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorException("Acesso negado", new Date()));
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
    	return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorException("Esse método não existe", new Date()));
    }
    
    
}

class ErrorException implements Serializable {
	private static final long serialVersionUID = 1L;

	public String error;
    public Date date;

    public ErrorException(String error, Date date) {
        this.error = error;
        this.date = date;
    }
}