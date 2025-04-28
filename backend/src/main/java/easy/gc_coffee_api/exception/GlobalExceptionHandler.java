package easy.gc_coffee_api.exception;

import easy.gc_coffee_api.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(GCException.class)
    public ResponseEntity<ErrorResponseDto> handleException(GCException exception) {
        Integer code = exception.getCode();
        return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), code), HttpStatusCode.valueOf(code));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException exception) {
        ObjectError firstError = exception.getBindingResult().getAllErrors().getFirst();
        return new ResponseEntity<>(new ErrorResponseDto(firstError.getDefaultMessage(), 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(IllegalArgumentException exception) {
        return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponseDto> handleMultipart(MultipartException exception) {
        return new ResponseEntity<>(new ErrorResponseDto("파일 업로드 형식이 잘못되었습니다.", 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParam(MissingServletRequestPartException exception) {
        return new ResponseEntity<>(new ErrorResponseDto("요청에 'file' 파라미터가 포함되어야 합니다.", 400), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new ErrorResponseDto(exception.getMessage(), 500), HttpStatusCode.valueOf(500));
    }
}
