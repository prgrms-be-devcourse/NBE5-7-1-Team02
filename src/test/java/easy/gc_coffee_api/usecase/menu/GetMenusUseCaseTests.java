package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.dto.MenusResponseDto;
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
import static org.junit.jupiter.api.Assertions.*;
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
    void get_menus_test() throws Exception {
        // given
        List<Menu> fakeMenus = Arrays.asList(
                new Menu("Americano", 3000, Category.COFFEE_BEAN, new Thumnail(null)),
                new Menu("Latte", 4000, Category.COFFEE_BEAN, new Thumnail(null))
        );
        when(menuRepository.findAll()).thenReturn(fakeMenus);
        // when
        MenusResponseDto result = getMenusUseCase.getMenus();

        // then
        assertThat(result.getMenus()).hasSize(2);
        assertThat(result.getMenus().getFirst().getName()).isEqualTo("Americano");
        assertThat(result.getMenus().get(1).getPrice()).isEqualTo(4000);
    }

}