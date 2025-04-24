package easy.gc_coffee_api.exception.menu;

import easy.gc_coffee_api.exception.GCException;

public class InvalidMenuCategoryException extends GCException {
    public InvalidMenuCategoryException(String message, Integer code) {
        super(message, code);
    }
}
