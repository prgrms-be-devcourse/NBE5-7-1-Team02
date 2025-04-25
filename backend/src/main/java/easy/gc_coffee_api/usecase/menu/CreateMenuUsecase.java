package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.ThumbnailCreateException;
import easy.gc_coffee_api.usecase.menu.helper.MenuSaver;
import easy.gc_coffee_api.usecase.menu.helper.ThumbnailFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuUsecase{

    private final MenuSaver menuSaver;
    private final ThumbnailFactory thumnailFatory;

    @Transactional
    public Long execute(String menuName, Category category, int price, Long fileId) throws ThumbnailCreateException {
        Thumbnail thumbnail = thumnailFatory.create(fileId);
        Menu menu = menuSaver.save(menuName, category, price, thumbnail);
        return menu.getId();
    }
}
