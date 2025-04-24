package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.exception.ThumnailCreateException;
import easy.gc_coffee_api.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThumnailFatory {

    private final FileRepository fileRepository;

    public Thumnail create(Long thumnailId) {
        if(thumnailId == null){
            return new Thumnail();
        }
        try {
            File file = getFile(thumnailId);
            return new Thumnail(file.getId(),file.getMimetype());
        }catch (IllegalArgumentException e){
            throw new ThumnailCreateException("image type이 아닙니다.");
        }
    }

    private File getFile(Long thumnailId) throws ThumnailCreateException {
        return fileRepository.findById(thumnailId).orElseThrow(()->new ThumnailCreateException("thumnail id로 파일을 찾을 수 없습니다."));
    }
}
