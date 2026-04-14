package iu.devinmehringer.project3.controller.exception;

public class ProtocolNotFoundException extends RuntimeException {
    public ProtocolNotFoundException(String message) {
        super(message);
    }
}
