package iu.devinmehringer.project3.controller.exception;

public class InvalidCreatePatientRequestException extends RuntimeException {
    public InvalidCreatePatientRequestException(String message) {
        super(message);
    }
}
