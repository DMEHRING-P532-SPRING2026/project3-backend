package iu.devinmehringer.project3.controller.exception;

public class ObservationNotFoundException extends RuntimeException {
    public ObservationNotFoundException(String message) {
        super(message);
    }
}
