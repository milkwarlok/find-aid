package md.luciddream.findaid.custom.exception;

public class TraumaAlreadyExistsException extends RuntimeException {
    public TraumaAlreadyExistsException() {
    }

    public TraumaAlreadyExistsException(String message) {
        super(message);
    }

    public TraumaAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TraumaAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public TraumaAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
