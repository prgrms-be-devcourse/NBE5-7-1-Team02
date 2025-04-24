package easy.gc_coffee_api.exception.menu;

import easy.gc_coffee_api.exception.GCException;

public class InvalidMenuNameException extends GCException {
    public InvalidMenuNameException(String message, Integer code) {
        super(message,code);
    }
}
