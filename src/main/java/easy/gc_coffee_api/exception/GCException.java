package easy.gc_coffee_api.exception;

import lombok.Getter;

public class GCException extends RuntimeException {

    @Getter
    private Integer code;

    public GCException(String message) {
        super(message);
    }

    public GCException(String message, Throwable cause) {
        super(message, cause);
    }
}
