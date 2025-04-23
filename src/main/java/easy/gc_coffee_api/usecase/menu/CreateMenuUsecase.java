package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateMenuUsecase{

    private final MenuRepository menuRepository;
    private final ThumnailFatory thumnailFatory;

    public Long process(String menuName, Category category, int price, Long fileId) {
        Thumnail thumnail = thumnailFatory.create(fileId);
        Menu menu = menuRepository.save( new Menu(menuName, price, category, null));
        return menu.getId();
    }

}
