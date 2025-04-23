package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.file.UploadFileResponseDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.usecase.file.UploadFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final UploadFileUseCase uploadFileUseCase;

    //TODO GlobalExceptionHandler에서 MultipartException, MissingServletRequestParameterException 처리
    @PostMapping
    public ResponseEntity<UploadFileResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            UploadFileResponseDto responseDto = uploadFileUseCase.execute(file);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IllegalArgumentException e) {
            throw new GCException(e.getMessage(), e, 400);
        } catch (Exception e) {
            throw new GCException(e.getMessage(), e, 500);
        }
    }
}
