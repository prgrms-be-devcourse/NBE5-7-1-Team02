package easy.gc_coffee_api.usecase.file;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.exception.file.FileNotFoundException;
import easy.gc_coffee_api.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileRemover {

    private final FileRepository fileRepository;

    @Transactional
    public void remove(Long id) throws FileNotFoundException, NullPointerException {
        File file = fileRepository
                .findByIdAndDeletedAtIsNull(Objects.requireNonNull(id))
                .orElseThrow(() -> new FileNotFoundException("해당 파일이 존재하지 않습니다.", 400));
        file.delete();
    }
}
