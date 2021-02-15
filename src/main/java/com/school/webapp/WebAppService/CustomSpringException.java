package com.school.webapp.WebAppService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomSpringException  {
    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<Object> handleException(MyException myException) {
        return new ResponseEntity<>(myException.getMessage(),HttpStatus.CONFLICT);
    }
}
