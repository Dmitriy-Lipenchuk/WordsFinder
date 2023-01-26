package application.exceptions;

public class WordAlreadyExistException extends RuntimeException{
    public WordAlreadyExistException() {
    }

    public WordAlreadyExistException(String message) {
        super(message);
    }

    public WordAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
