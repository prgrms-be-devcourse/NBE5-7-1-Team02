package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.dto.MenusResponseDto;
import easy.gc_coffee_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMenusUseCase {
    private final MenuRepository menuRepository;

    public MenusResponseDto getMenus() {
        List<MenuResponseDto> menus = menuRepository.findAllNotDeleted();

        return new MenusResponseDto(menus);
    }
}
