package com.neotech.countrybyphone.app;

import com.neotech.countrybyphone.app.model.error.ErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<ErrorModel> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());

        return new ResponseEntity<>(errors, headers, status);
    }
}
