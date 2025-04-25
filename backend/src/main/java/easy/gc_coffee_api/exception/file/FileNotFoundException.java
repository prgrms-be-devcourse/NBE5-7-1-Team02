package easy.gc_coffee_api.exception.file;

import easy.gc_coffee_api.exception.GCException;

public class FileNotFoundException extends GCException {
    public FileNotFoundException(String message, Integer code) {
        super(message, code);
    }
}
