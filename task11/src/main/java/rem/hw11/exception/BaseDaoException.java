package rem.hw11.exception;

public class BaseDaoException extends RuntimeException {
    public BaseDaoException() {
    }

    public BaseDaoException(String message) {
        super(message);
    }

    public BaseDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
