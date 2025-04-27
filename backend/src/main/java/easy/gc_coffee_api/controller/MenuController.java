package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.*;
import easy.gc_coffee_api.exception.ThumbnailCreateException;
import easy.gc_coffee_api.exception.menu.MenuNotFoundException;
import easy.gc_coffee_api.usecase.menu.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import easy.gc_coffee_api.exception.GCException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final CreateMenuUsecase createMenuUsecase;
    private final UpdateMenuUsecase updateMenuUsecase;
    private final GetMenusUseCase getMenusUseCase;
    private final DeleteMenuUseCase deleteMenuUseCase;
    private final GetMenuUseCase getMenuUseCase;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CreateMenuResponseDto> create(@RequestBody @Validated CreateMenuRequestDto requestDto) {
        try {
            Long id = createMenuUsecase.execute(
                    requestDto.getMenuName(),
                    requestDto.getCategory(),
                    requestDto.getPrice(),
                    requestDto.getThumbnailId()
            );
            return ResponseEntity.ok(new CreateMenuResponseDto(id));
        } catch (ThumbnailCreateException | EntityNotFoundException e) {
            throw new GCException(e.getMessage(), e, 400);
        }
    }

    @PutMapping("/{menuId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateMenu(@PathVariable Long menuId, @RequestBody @Validated UpdateMenuRequestDto requestDto) {
        updateMenuUsecase.execute(menuId, requestDto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<MenusResponseDto> getMenus() {
        MenusResponseDto responseDto = getMenusUseCase.execute();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long menuId) {
        MenuResponseDto responseDto = getMenuUseCase.execute(menuId);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{menuId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        try {
            deleteMenuUseCase.execute(menuId);
            return ResponseEntity.noContent().build();
        } catch (MenuNotFoundException e) {
            throw new GCException(e.getMessage(), e, 400);
        }
    }

}
