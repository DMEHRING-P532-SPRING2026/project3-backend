package iu.devinmehringer.project3.controller.exception;

public class CommandLogNotFoundException extends RuntimeException {
    public CommandLogNotFoundException(String message) {
        super(message);
    }
}
