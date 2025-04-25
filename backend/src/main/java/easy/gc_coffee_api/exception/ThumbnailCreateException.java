package easy.gc_coffee_api.exception;

public class ThumbnailCreateException extends RuntimeException{
    public ThumbnailCreateException(String message) {
        super(message);
    }

    public ThumbnailCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
