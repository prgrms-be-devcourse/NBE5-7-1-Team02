package easy.gc_coffee_api.controller;

import easy.gc_coffee_api.dto.CreateMenuRequestDto;
import easy.gc_coffee_api.dto.CreateMenuResponseDto;
import easy.gc_coffee_api.exception.GCException;
import easy.gc_coffee_api.exception.ThumnailCreateException;
import easy.gc_coffee_api.usecase.menu.CreateMenuUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final CreateMenuUsecase createMenuUsecase;

    @PostMapping("/menus")
    public ResponseEntity<CreateMenuResponseDto> create(@RequestBody @Validated CreateMenuRequestDto requestDto) {
        try {
            Long id = createMenuUsecase.excute(
                    requestDto.getMenuName(),
                    requestDto.getCategory(),
                    requestDto.getPrice(),
                    requestDto.getThumnailId()
            );
            return ResponseEntity.ok(new CreateMenuResponseDto(id));
        } catch (ThumnailCreateException thumnailCreateException) {
            throw new GCException(thumnailCreateException.getMessage(), thumnailCreateException, 400);
        }
    }
}
