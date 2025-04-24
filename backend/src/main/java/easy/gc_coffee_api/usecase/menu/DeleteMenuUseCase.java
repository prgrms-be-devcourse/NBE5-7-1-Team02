package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.file.FileRemover;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuUseCase {

    private final MenuRepository menuRepository;
    private final FileRemover fileRemover;

    @Transactional
    public void execute(Long menuId) throws MenuNotFoundException {
        Menu existMenu = menuRepository
                .findByIdAndDeletedAtIsNull(menuId)
                .orElseThrow(() -> new MenuNotFoundException("이미 삭제된 메뉴이거나 존재하지 않는 메뉴입니다.", 400));
        existMenu.delete();

        if (existMenu.hasThumbNail()) {
            fileRemover.remove(existMenu.getThumnailId());
        }
    }
}


