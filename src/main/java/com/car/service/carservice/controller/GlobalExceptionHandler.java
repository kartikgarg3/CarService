package com.car.service.carservice.controller;

import com.car.service.carservice.exception.AppointmentNotFoundException;
import com.car.service.carservice.exception.NoOperatorAvailableException;
import com.car.service.carservice.exception.OperatorNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleNoAvailableOperatorException(NoOperatorAvailableException e) {
        return new ResponseEntity<>("No operator is available for given timeslot", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNoAvailableOperatorException(OperatorNotAvailableException e) {
        return new ResponseEntity<>("Operator is not available for given timeslot", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNoAvailableOperatorException(AppointmentNotFoundException e) {
        return new ResponseEntity<>("No appointment exists with given id", HttpStatus.OK);
    }
}
