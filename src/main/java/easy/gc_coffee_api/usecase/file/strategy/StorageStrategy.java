package easy.gc_coffee_api.usecase.file.strategy;

import easy.gc_coffee_api.usecase.file.vo.FileUrl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageStrategy {
    FileUrl upload(MultipartFile file, String newFileName) throws IOException;
}