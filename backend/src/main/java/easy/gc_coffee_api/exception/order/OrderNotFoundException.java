package easy.gc_coffee_api.exception.order;

import easy.gc_coffee_api.exception.GCException;

public class OrderNotFoundException extends GCException {
    public OrderNotFoundException(String message, Integer code) {
        super(message,code);
    }
}
