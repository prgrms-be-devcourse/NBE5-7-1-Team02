package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.*;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.usecase.menu.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.exception.ThumnailCreateException;
import jakarta.persistence.EntityNotFoundException;
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
    private final GetMenuUseCase getMenuUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;

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
        } catch (ThumnailCreateException | EntityNotFoundException e) {
            throw new GCException(e.getMessage(), e, 400);
        }
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable Long menuId, @RequestBody @Validated UpdateMenuRequestDto requestDto) {
        updateMenuUsecase.execute(menuId, requestDto);
        return ResponseEntity.ok("수정 완료");
    }

    @GetMapping("/menus")
    public ResponseEntity<MenusResponseDto> getMenus() {
        MenusResponseDto responseDto = getMenusUseCase.execute();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long menuId) {
        MenuResponseDto responseDto = getMenuUseCase.execute(menuId);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        try {
            deleteMenuUseCase.execute(menuId);
            return ResponseEntity.ok("삭제 완료");
        } catch (MenuNotFoundException e) {
            throw new GCException(e.getMessage(), e, 400);
        }
    }

}
