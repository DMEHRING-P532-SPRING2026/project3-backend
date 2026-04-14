package iu.devinmehringer.project3.controller.exception;

public class InvalidCreateObservationException extends RuntimeException {
    public InvalidCreateObservationException(String message) {
        super(message);
    }
}
