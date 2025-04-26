package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.dto.MenusResponseDto;
import easy.gc_coffee_api.usecase.menu.dto.MenuDatas;
import easy.gc_coffee_api.usecase.menu.helper.MenuReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMenusUseCase {
    private final MenuReader menuReader;

    public MenusResponseDto execute() {
        MenuDatas menus = menuReader.readAllNotDelete();
        return new MenusResponseDto(menus
                .transform((menu)->
                        new MenuResponseDto(
                                menu.getId(),
                                menu.getName(),
                                menu.getPrice(),
                                menu.getCategory(),
                                menu.getThumbnailUrl(),
                                menu.getFileId()
                                )
                        )
        );
    }
}
