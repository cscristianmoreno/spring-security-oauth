package com.example.myapp.security;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> CustomBadCredentialsException() {
        return new ResponseEntity<String>("Las credenciales son incorrectas", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> CustomSQLIntegrityConstraintViolationException() {
        return new ResponseEntity<String>("Ya existe un usuario con este email", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> expiredJwtException() {
        return new ResponseEntity<String>("La autenticación ha expirado", HttpStatus.UNAUTHORIZED);
    }
}
