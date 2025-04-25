package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMenuUseCase {
    private final MenuRepository menuRepository;

    public MenuResponseDto execute(Long menuId) {
        return menuRepository.findOneNotDeleted(menuId).
                orElseThrow(() -> new MenuNotFoundException("이미 삭제된 메뉴이거나 존재하지 않는 메뉴입니다.", 400));
    }
}
