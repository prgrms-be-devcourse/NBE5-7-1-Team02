package easy.gc_coffee_api.exception.menu;

import easy.gc_coffee_api.exception.GCException;

public class InvalidMenuPriceException extends GCException {
    public InvalidMenuPriceException(String message,Integer code) {
        super(message,code);
    }
}
