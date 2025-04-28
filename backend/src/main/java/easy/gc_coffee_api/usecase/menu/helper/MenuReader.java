package easy.gc_coffee_api.usecase.menu.helper;

import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.file.FileUrlTranslator;
import easy.gc_coffee_api.usecase.menu.dto.MenuData;
import easy.gc_coffee_api.usecase.menu.dto.MenuDatas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuReader {

    private final MenuRepository menuRepository;
    private final FileUrlTranslator fileUrlTranslator;

    public MenuDatas readAllNotDelete() {
        List<MenuData> menus = menuRepository.findAllNotDeleted();
        menus.forEach(this::updateFullPath);
        return new MenuDatas(menus);
    }

    public MenuData findOneNotDeleted(Long menuId) {
        MenuData menu = menuRepository.findOneNotDeleted(menuId).
                orElseThrow(() -> new MenuNotFoundException("이미 삭제된 메뉴이거나 존재하지 않는 메뉴입니다.", 400));
        return updateFullPath(menu);
    }

    private MenuData updateFullPath(MenuData menu) {
        if (menu.hasThumbnail()) {
            menu.setFullPathUrl(fileUrlTranslator.execute(menu.getThumbnailUrl()));
        }
        return menu;
    }
}
