package rem.hw11.exception;

public class DBServiceException extends RuntimeException {
    public DBServiceException() {
    }

    public DBServiceException(String message) {
        super(message);
    }

    public DBServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
