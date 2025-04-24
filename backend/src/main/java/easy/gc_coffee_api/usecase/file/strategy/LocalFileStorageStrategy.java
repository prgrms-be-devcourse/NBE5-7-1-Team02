package easy.gc_coffee_api.usecase.file.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Component("localStorage")
public class LocalFileStorageStrategy implements StorageStrategy {
    @Value("${file.storage_path}")
    private String STORAGE_PATH;

    @Override
    public void upload(MultipartFile file, String newFileName) throws IOException {
        Path savePath = Paths.get(STORAGE_PATH + newFileName);

        file.transferTo(savePath.toFile());
    }
}
