package iu.devinmehringer.project3.controller.exception;

public class PhenomenonNotFoundException extends RuntimeException {
    public PhenomenonNotFoundException(String message) {
        super(message);
    }
}
