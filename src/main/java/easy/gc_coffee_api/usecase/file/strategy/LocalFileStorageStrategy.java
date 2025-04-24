package easy.gc_coffee_api.usecase.file.strategy;

import easy.gc_coffee_api.usecase.file.vo.FileUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Component("localStorage")
public class LocalFileStorageStrategy implements StorageStrategy {
    private static final String BASE_PATH = System.getProperty("user.dir");
    private static final String DIR = "/storage/";

    @Override
    public FileUrl upload(MultipartFile file, String newFileName) throws IOException {
        Path savePath = Paths.get(BASE_PATH + DIR + newFileName);

        Files.createDirectories(savePath.getParent());

        file.transferTo(savePath.toFile());

        return new FileUrl(DIR + newFileName);
    }
}
