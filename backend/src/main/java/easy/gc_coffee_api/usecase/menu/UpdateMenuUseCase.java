package easy.gc_coffee_api.usecase.menu;


import easy.gc_coffee_api.dto.menu.UpdateMenuRequestDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.exception.menu.InvalidMenuCategoryException;
import easy.gc_coffee_api.exception.menu.InvalidMenuNameException;
import easy.gc_coffee_api.exception.menu.InvalidMenuPriceException;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.menu.helper.ThumbnailFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UpdateMenuUseCase {

    private final MenuRepository menuRepository;
    private final ThumbnailFactory thumbnailFactory;

    @Transactional
    public void execute(Long menuId, UpdateMenuRequestDto requestDto) throws ResponseStatusException, IllegalArgumentException {
        Menu menu = menuRepository
                .findByIdAndDeletedAtIsNull(menuId)
                .orElseThrow(() -> new MenuNotFoundException("메뉴가 없습니다.", 404));

        if (requestDto.getName() == null) {
            throw new InvalidMenuNameException("이름을 입력하지 않았습니다.", 400);
        }
        if (requestDto.getCategory() == null) {
            throw new InvalidMenuCategoryException("카테고리를 입력하지 않았습니다.", 400);
        }
        if (requestDto.getPrice() == null) {
            throw new InvalidMenuPriceException("가격을 입력하지 않았습니다.", 400);
        }
        if (requestDto.getPrice() < 0) {
            throw new InvalidMenuPriceException("가격은 음수일 수 없습니다.", 400);
        }

        Category category = Category.valueOf(requestDto.getCategory().toUpperCase());
        Thumbnail thumbnail = thumbnailFactory.create(requestDto.getImageId());


        menu.update(requestDto.getName(),
                requestDto.getPrice(),
                category,
                thumbnail);

    }

}
