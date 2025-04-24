package easy.gc_coffee_api.exception.menu;

import easy.gc_coffee_api.exception.GCException;

public class MenuNotFoundException extends GCException {
    public MenuNotFoundException(String message, Integer code) {
        super(message,code);
    }
}
