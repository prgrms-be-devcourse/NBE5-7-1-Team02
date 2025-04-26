package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.usecase.menu.dto.MenuData;
import easy.gc_coffee_api.usecase.menu.helper.MenuReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMenuUseCase {
    private final MenuReader menuReader;

    public MenuResponseDto execute(Long menuId) {
        MenuData menuData = menuReader.findOneNotDeleted(menuId);
        return new MenuResponseDto(
                menuData.getId(),
                menuData.getName(),
                menuData.getPrice(),
                menuData.getCategory(),
                menuData.getThumbnailUrl(),
                menuData.getFileId()
        );
    }
}
