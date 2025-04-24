package easy.gc_coffee_api.usecase.menu;


import easy.gc_coffee_api.dto.UpdateMenuRequestDto;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
import easy.gc_coffee_api.usecase.file.SaveFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UpdateMenuUsecase {

    private final MenuRepository menuRepository;
    private final ThumnailFatory thumnailFatory;

    public void execute(Long menuId, UpdateMenuRequestDto requestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "메뉴없어"));

        if(requestDto.getName()==null){
            throw new IllegalArgumentException("Name cannot be null");
        }
        if(requestDto.getCategory()==null){
            throw new IllegalArgumentException("Category cannot be null.");
        }
        if(requestDto.getPrice()<0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        Category category = Category.valueOf(requestDto.getCategory().toUpperCase());
        Thumnail thumnail = thumnailFatory.create(requestDto.getImageId());



        menu.update(requestDto.getName(),
                requestDto.getPrice(),
                category,
                thumnail);

    }

}
