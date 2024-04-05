package main.java.exception;

public class InputArgumentException extends Exception{

    private final String message;

    public InputArgumentException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
