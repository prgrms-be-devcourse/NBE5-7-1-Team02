package easy.gc_coffee_api.service;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuResponseDto> getMenus() {
        List<MenuResponseDto> menus = new ArrayList<>();
        List<Menu> all = menuRepository.findAll();
        for(Menu menu : all){
            MenuResponseDto menuResponseDto = MenuResponseDto.builder()
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .category(menu.getCategory())
                    .build();
            menus.add(menuResponseDto);
        }
        return menus;
    }
}
