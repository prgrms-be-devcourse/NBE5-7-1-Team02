package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenuResponseDto;
import easy.gc_coffee_api.dto.MenusResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Menu;
import easy.gc_coffee_api.entity.Thumnail;
import easy.gc_coffee_api.entity.common.Category;
import easy.gc_coffee_api.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMenusUseCaseTests {
    @Mock
    private MenuRepository menuRepository;

    private GetMenusUseCase getMenusUseCase;

    @BeforeEach
    void setUp() {
        getMenusUseCase = new GetMenusUseCase(menuRepository);
    }

    @Test
    @DisplayName("메뉴 리스트 조회")
    void get_menus_test() {

        // given
        MenuResponseDto firstDto =  new MenuResponseDto("Americano", 3000, Category.COFFEE_BEAN, "/storage/001.jpeg");
        MenuResponseDto secondDto = new MenuResponseDto("CafeLatte", 4000, Category.COFFEE_BEAN, null);
        List<MenuResponseDto> fakeMenus = Arrays.asList(
                firstDto,secondDto
        );

        when(menuRepository.findAllByMenuResponseDto()).thenReturn(fakeMenus);

        // when
        MenusResponseDto result = getMenusUseCase.getMenus();

        // then
        MenuResponseDto first = result.getMenus().getFirst();
        assertThat(first.getName()).isEqualTo(firstDto.getName());
        assertThat(first.getPrice()).isEqualTo(firstDto.getPrice());
        assertThat(first.getCategory()).isEqualTo(firstDto.getCategory());
        assertThat(first.getThumbnailUrl()).isEqualTo(firstDto.getThumbnailUrl());

        MenuResponseDto second = result.getMenus().get(1);
        assertThat(second.getName()).isEqualTo(secondDto.getName());
        assertThat(second.getPrice()).isEqualTo(secondDto.getPrice());
        assertThat(second.getCategory()).isEqualTo(secondDto.getCategory());
        assertThat(second.getThumbnailUrl()).isNull();

    }
}