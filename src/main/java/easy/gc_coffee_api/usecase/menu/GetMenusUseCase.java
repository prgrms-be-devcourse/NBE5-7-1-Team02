package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.dto.MenusResponseDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetMenusUseCase {
    private final MenuRepository menuRepository;

    public MenusResponseDto getMenus() {
        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponseDto> menus = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuResponseDto dto = MenuResponseDto.builder()
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .category(menu.getCategory())
                    .build();
            menus.add(dto);
        }
        return new MenusResponseDto(menus);
    }
}
