package iu.devinmehringer.project3.controller.exception;

public class InvalidObservationException extends RuntimeException {
    public InvalidObservationException(String message) {
        super(message);
    }
}
