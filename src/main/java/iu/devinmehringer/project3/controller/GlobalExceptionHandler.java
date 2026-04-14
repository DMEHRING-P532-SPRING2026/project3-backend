package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCreateObservationException.class)
    public ResponseEntity<String> invalidCreateObservationException(InvalidCreateObservationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCreatePatientRequestException.class)
    public ResponseEntity<String> invalidCreatePatientRequestException(InvalidCreatePatientRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCreatePhenomenonRequestException.class)
    public ResponseEntity<String> invalidCreatePhenomenonRequestException(InvalidCreatePhenomenonRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCreateProtocolException.class)
    public ResponseEntity<String> invalidCreateProtocolException(InvalidCreateProtocolException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(PhenomenonTypeNotFoundException.class)
    public ResponseEntity<String> phenomenonTypeNotFoundException(PhenomenonTypeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ProtocolNotFoundException.class)
    public ResponseEntity<String> protocolNotFoundException(ProtocolNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientNotFoundException(PatientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PhenomenonNotFoundException.class)
    public ResponseEntity<String> phenomenonNotFoundException(PhenomenonNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidObservationException.class)
    public ResponseEntity<String> invalidObservationException(InvalidObservationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ObservationNotFoundException.class)
    public ResponseEntity<String> observationNotFoundException(ObservationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
