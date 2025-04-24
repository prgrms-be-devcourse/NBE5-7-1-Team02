package easy.gc_coffee_api.usecase.file.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageStrategy {
    void upload(MultipartFile file, String newFileName) throws IOException;
}