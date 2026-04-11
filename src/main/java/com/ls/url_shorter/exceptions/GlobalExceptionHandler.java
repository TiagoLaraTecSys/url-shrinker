package com.ls.url_shorter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler{


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationError(MethodArgumentNotValidException ex) {

        ResponseError responseError = new ResponseError();
        responseError.setCode("VALIDATION ERROR");
        List<ResponseError.Errors> errorsList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ResponseError.Errors(e.getField(), e.getDefaultMessage()))
                .toList();

        responseError.setErrors(errorsList);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Object> urlNotFoundException(UrlNotFoundException e) {

        return ResponseEntity.notFound().build();
    }
}