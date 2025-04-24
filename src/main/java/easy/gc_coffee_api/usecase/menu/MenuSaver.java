package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuSaver {

    private final MenuRepository menuRepository;

    public Menu save(String menuName, Category category, int price, Thumnail thumnail) {
        return menuRepository.save(new Menu(menuName, price, category, thumnail));
    }
}