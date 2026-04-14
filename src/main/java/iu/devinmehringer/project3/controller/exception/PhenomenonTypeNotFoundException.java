package iu.devinmehringer.project3.controller.exception;

public class PhenomenonTypeNotFoundException extends RuntimeException {
    public PhenomenonTypeNotFoundException(String message) {
        super(message);
    }
}
