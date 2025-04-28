package easy.gc_coffee_api.usecase.menu.helper;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.exception.ThumbnailCreateException;
import easy.gc_coffee_api.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThumbnailFactory {

    private final FileRepository fileRepository;

    public Thumbnail create(Long thumbnailId) {
        if (thumbnailId == null) {
            return new Thumbnail();
        }
        try {
            File file = getFile(thumbnailId);
            return new Thumbnail(file.getId(), file.getMimetype());
        } catch (IllegalArgumentException e) {
            throw new ThumbnailCreateException("image type이 아닙니다.");
        }
    }

    private File getFile(Long thumbnailId) throws ThumbnailCreateException {
        return fileRepository.findById(thumbnailId).orElseThrow(() -> new ThumbnailCreateException("thumbnail id로 파일을 찾을 수 없습니다."));
    }
}
