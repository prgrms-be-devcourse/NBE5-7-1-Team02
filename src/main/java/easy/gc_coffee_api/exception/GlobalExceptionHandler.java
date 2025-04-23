package easy.gc_coffee_api.exception;

import easy.gc_coffee_api.dto.ErrorResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(GCException.class)
    public ResponseEntity<ErrorResponseDto> handleException(GCException exception) {
        Integer code = exception.getCode();
        return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), code), HttpStatusCode.valueOf(code));
    }
}
