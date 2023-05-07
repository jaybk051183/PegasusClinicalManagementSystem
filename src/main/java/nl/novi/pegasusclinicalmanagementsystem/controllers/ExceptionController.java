package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.exceptions.AppointmentNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.BadRequestException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.PatientNameTooLongException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.PatientNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.RecordNotFoundException;
import nl.novi.pegasusclinicalmanagementsystem.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppointmentNotFoundException.class)
    protected ResponseEntity<Object> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
        String errorMessage = "Appointment not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        String errorMessage = "Bad request";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNameTooLongException.class)
    protected ResponseEntity<Object> handlePatientNameTooLongException(PatientNameTooLongException ex) {
        String errorMessage = "Patient name is too long";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    protected ResponseEntity<Object> handlePatientNotFoundException(PatientNotFoundException ex) {
        String errorMessage = "Patient not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex) {
        String errorMessage = "Record not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String errorMessage = "Username not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
