package easy.gc_coffee_api.usecase.file.strategy;

import easy.gc_coffee_api.usecase.file.FileUrlTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Component("localStorage")
public class LocalFileStorageStrategy implements StorageStrategy {

    private final FileUrlTranslator translator;

    @Override
    public void upload(MultipartFile file, String newFileName) throws IOException {
        Path savePath = Paths.get(translator.execute(newFileName));

        file.transferTo(savePath.toFile());
    }
}
