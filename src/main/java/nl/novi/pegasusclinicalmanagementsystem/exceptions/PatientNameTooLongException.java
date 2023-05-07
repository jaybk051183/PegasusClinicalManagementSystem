package nl.novi.pegasusclinicalmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PatientNameTooLongException extends RuntimeException {

    public PatientNameTooLongException(String message) {
        super(message);
    }

    public PatientNameTooLongException(String message, Throwable cause) {
        super(message, cause);
    }
}
