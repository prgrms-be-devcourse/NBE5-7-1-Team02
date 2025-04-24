package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.UpdateMenuRequestDto;
import easy.gc_coffee_api.usecase.menu.UpdateMenuUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import easy.gc_coffee_api.dto.CreateMenuRequestDto;
import easy.gc_coffee_api.dto.CreateMenuResponseDto;
import easy.gc_coffee_api.dto.MenusResponseDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.exception.ThumnailCreateException;
import easy.gc_coffee_api.usecase.menu.CreateMenuUsecase;
import jakarta.persistence.EntityNotFoundException;
import easy.gc_coffee_api.usecase.menu.GetMenusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final CreateMenuUsecase createMenuUsecase;
    private final UpdateMenuUsecase updateMenuUsecase;
    private final GetMenusUseCase getMenusUseCase;

    @PostMapping("/menus")
    public ResponseEntity<CreateMenuResponseDto> create(@RequestBody @Validated CreateMenuRequestDto requestDto) {
        try {
            Long id = createMenuUsecase.execute(
                    requestDto.getMenuName(),
                    requestDto.getCategory(),
                    requestDto.getPrice(),
                    requestDto.getThumnailId()
            );
            return ResponseEntity.ok(new CreateMenuResponseDto(id));
        } catch (ThumnailCreateException | EntityNotFoundException e ) {
            throw new GCException(e.getMessage(), e, 400);
        }
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable Long menuId,@RequestBody @Validated UpdateMenuRequestDto requestDto ) {
        updateMenuUsecase.execute(menuId, requestDto);
        return ResponseEntity.ok("수정 완료");
    }


    @GetMapping("/menus")
    public ResponseEntity<MenusResponseDto> getMenus() {
        MenusResponseDto responseDto = getMenusUseCase.execute();
        return ResponseEntity.ok(responseDto);
    }
}
