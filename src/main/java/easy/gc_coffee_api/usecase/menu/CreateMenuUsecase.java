package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.ThumnailCreateException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuUsecase{

    private final MenuSaver menuSaver;
    private final ThumnailFatory thumnailFatory;

    @Transactional
    public Long execute(String menuName, Category category, int price, Long fileId) throws ThumnailCreateException {
        Thumnail thumnail = thumnailFatory.create(fileId);
        Menu menu = menuSaver.save(menuName, category, price, thumnail);
        return menu.getId();
    }
}
