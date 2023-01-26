package application.exceptions;

public class NonCyrillicStringException extends RuntimeException {
    public NonCyrillicStringException() {
    }

    public NonCyrillicStringException(String message) {
        super(message);
    }

    public NonCyrillicStringException(Throwable cause) {
        super(cause);
    }
}
