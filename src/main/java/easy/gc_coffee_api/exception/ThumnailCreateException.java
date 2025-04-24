package easy.gc_coffee_api.exception;

public class ThumnailCreateException extends RuntimeException{
    public ThumnailCreateException(String message) {
        super(message);
    }

    public ThumnailCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
