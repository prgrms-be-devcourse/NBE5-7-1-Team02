package easy.gc_coffee_api.exception;

import lombok.Getter;

public class GCException extends RuntimeException {

    @Getter
    private final Integer code;

    public GCException(String message,Integer code) {
        super(message);
        this.code = code;
    }

    public GCException(String message, Throwable cause,Integer code) {
        super(message, cause);
        this.code = code;
    }
}
