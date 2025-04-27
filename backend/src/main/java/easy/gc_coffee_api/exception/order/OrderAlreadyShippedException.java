package easy.gc_coffee_api.exception.order;

import easy.gc_coffee_api.exception.GCException;

public class OrderAlreadyShippedException extends GCException {
    public OrderAlreadyShippedException(String message, Integer code) {
        super(message,code);
    }
}
