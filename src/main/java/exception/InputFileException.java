package exception;

public class InputFileException extends Exception{

    private final String message;

    public InputFileException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
