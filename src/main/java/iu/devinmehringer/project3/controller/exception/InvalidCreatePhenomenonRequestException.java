package iu.devinmehringer.project3.controller.exception;

public class InvalidCreatePhenomenonRequestException extends RuntimeException {
    public InvalidCreatePhenomenonRequestException(String message) {
        super(message);
    }
}
